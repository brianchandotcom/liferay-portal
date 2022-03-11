/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.ContentDocument;
import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.Geo;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLink;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.jaxrs.context.EntityExtensionContext;
import com.liferay.portal.vulcan.jaxrs.context.ExtensionContext;

import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentResourceTest
	extends BaseStructuredContentResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmComplexStructure = _addDDMStructure(
			testGroup, "test-ddm-complex-structure.json");
		_ddmLocalizedStructure = _addDDMStructure(
			testGroup, "test-ddm-localized-structure.json");

		_ddmStructure = _addDDMStructure(testGroup, "test-ddm-structure.json");

		_ddmTemplate = _addDDMTemplate(_ddmStructure);

		_depotDDMStructure = _addDDMStructure(
			testDepotEntry.getGroup(), "test-ddm-structure.json");

		DLFolder dlFolder = DLTestUtil.addDLFolder(testGroup.getGroupId());

		_dlFileEntry = DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

		_irrelevantDDMStructure = _addDDMStructure(
			irrelevantGroup, "test-ddm-structure.json");

		_addDDMTemplate(_irrelevantDDMStructure);

		_irrelevantJournalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());
		_journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		_layout = LayoutTestUtil.addTypeContentLayout(testGroup);
	}

	@Override
	@Test
	public void testGetStructuredContent() throws Exception {

		// Get structured content

		super.testGetStructuredContent();

		// Complete structured content with all types of content fields

		StructuredContent completeStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), _randomCompleteStructuredContent());

		StructuredContent structuredContent =
			structuredContentResource.getStructuredContent(
				completeStructuredContent.getId());

		assertEquals(completeStructuredContent, structuredContent);
		assertValid(structuredContent);

		// Different locale

		structuredContent = structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());

		String title = structuredContent.getTitle();

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		StructuredContentResource frenchStructuredContentResource =
			builder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.FRANCE
			).build();

		String frenchTitle = RandomTestUtil.randomString();

		structuredContent.setTitle(frenchTitle);

		frenchStructuredContentResource.putStructuredContent(
			structuredContent.getId(), structuredContent);

		structuredContent =
			frenchStructuredContentResource.getStructuredContent(
				structuredContent.getId());

		Assert.assertEquals(frenchTitle, structuredContent.getTitle());

		structuredContent = structuredContentResource.getStructuredContent(
			structuredContent.getId());

		Assert.assertEquals(title, structuredContent.getTitle());

		// Extension

		Bundle bundle = FrameworkUtil.getBundle(
			StructuredContentResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				ContextResolver.class, new ExtensionContextResolver(),
				HashMapDictionaryBuilder.put(
					"osgi.jaxrs.application.select",
					"(osgi.jaxrs.name=Liferay.Headless.Delivery)"
				).put(
					"osgi.jaxrs.extension", "true"
				).build());

		structuredContent = structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());

		HttpInvoker.HttpResponse httpResponse =
			structuredContentResource.getStructuredContentHttpResponse(
				structuredContent.getId());

		String content = httpResponse.getContent();

		Assert.assertTrue(content.contains("version"));

		structuredContent = StructuredContentSerDes.toDTO(content);

		Assert.assertNull(structuredContent.getTitle());

		serviceRegistration.unregister();

		// Role admin user

		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Map<String, Map<String, String>> actions =
			getStructuredContent.getActions();

		Assert.assertTrue(actions.containsKey("delete"));
		Assert.assertTrue(actions.containsKey("get"));
		Assert.assertTrue(actions.containsKey("get-rendered-content"));
		Assert.assertTrue(actions.containsKey("replace"));
		Assert.assertTrue(actions.containsKey("subscribe"));
		Assert.assertTrue(actions.containsKey("unsubscribe"));
		Assert.assertTrue(actions.containsKey("update"));

		// Role owner

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), "com.liferay.journal",
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.ADD_ARTICLE);

		String password = RandomTestUtil.randomString();

		User ownerUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			ownerUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {ownerUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		StructuredContentResource ownerUserStructuredContentResource =
			builder.authentication(
				ownerUser.getLogin(), password
			).locale(
				LocaleUtil.getDefault()
			).build();

		postStructuredContent =
			ownerUserStructuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		getStructuredContent =
			ownerUserStructuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		try {
			actions = getStructuredContent.getActions();

			Assert.assertTrue(actions.containsKey("delete"));
			Assert.assertTrue(actions.containsKey("get"));
			Assert.assertTrue(actions.containsKey("get-rendered-content"));
			Assert.assertTrue(actions.containsKey("replace"));
			Assert.assertTrue(actions.containsKey("subscribe"));
			Assert.assertTrue(actions.containsKey("unsubscribe"));
			Assert.assertTrue(actions.containsKey("update"));
		}
		finally {
			_roleLocalService.deleteRole(role);
		}

		// Role regular user

		role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), JournalArticle.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.VIEW);

		User regularUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			regularUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {regularUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		builder = StructuredContentResource.builder();

		StructuredContentResource regularUserStructuredContentResource =
			builder.authentication(
				regularUser.getLogin(), password
			).locale(
				LocaleUtil.getDefault()
			).build();

		getStructuredContent =
			regularUserStructuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		try {
			actions = getStructuredContent.getActions();

			Assert.assertFalse(actions.containsKey("delete"));
			Assert.assertTrue(actions.containsKey("get"));
			Assert.assertTrue(actions.containsKey("get-rendered-content"));
			Assert.assertFalse(actions.containsKey("replace"));
			Assert.assertFalse(actions.containsKey("subscribe"));
			Assert.assertFalse(actions.containsKey("unsubscribe"));
			Assert.assertFalse(actions.containsKey("update"));
		}
		finally {
			_roleLocalService.deleteRole(role);
			_userLocalService.deleteUser(regularUser);
			_userLocalService.deleteUser(ownerUser);
		}
	}

	@Override
	@Test
	public void testGetStructuredContentRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				testGroup.getCreatorUserId(), testGroup.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				_ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				false, 0, 0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId()));

		String structuredContentRenderedContentByDisplayPageDisplayPageKey =
			structuredContentResource.
				getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
					journalArticle.getResourcePrimKey(),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey());

		Assert.assertNotNull(
			structuredContentRenderedContentByDisplayPageDisplayPageKey);
	}

	@Override
	@Test
	public void testGetStructuredContentRenderedContentContentTemplate()
		throws Exception {

		StructuredContent structuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		ContentField[] contentFields = structuredContent.getContentFields();

		ContentFieldValue contentFieldValue =
			contentFields[0].getContentFieldValue();

		Assert.assertEquals(
			"<div>" + contentFieldValue.getData() + "</div>",
			structuredContentResource.
				getStructuredContentRenderedContentContentTemplate(
					structuredContent.getId(), _ddmTemplate.getTemplateKey()));
	}

	@Override
	@Test
	public void testPostSiteStructuredContent() throws Exception {
		super.testPostSiteStructuredContent();

		StructuredContent randomLocalizedStructuredContent =
			_randomLocalizedStructuredContent();

		StructuredContent postStructuredContent =
			testPostSiteStructuredContent_addStructuredContent(
				randomLocalizedStructuredContent);

		assertEquals(randomLocalizedStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	public static class ExtensionContextResolver
		implements ContextResolver<ExtensionContext> {

		@Override
		public ExtensionContext getContext(Class<?> type) {
			if (com.liferay.headless.delivery.dto.v1_0.StructuredContent.class.
					isAssignableFrom(type)) {

				return new EntityExtensionContext
					<com.liferay.headless.delivery.dto.v1_0.
						StructuredContent>() {

					@Override
					public Map<String, Object> getEntityExtendedProperties(
						com.liferay.headless.delivery.dto.v1_0.StructuredContent
							structuredContent) {

						return HashMapBuilder.<String, Object>put(
							"version", "1.0"
						).build();
					}

					@Override
					public Set<String> getEntityFilteredPropertyKeys(
						com.liferay.headless.delivery.dto.v1_0.StructuredContent
							structuredContent) {

						return Collections.singleton("title");
					}

				};
			}

			return null;
		}

	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"contentStructureId", "description", "priority", "title"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"assetLibraryId", "contentStructureId", "creatorId", "siteId"
		};
	}

	@Override
	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_irrelevantDDMStructure.getStructureId());

		return structuredContent;
	}

	@Override
	protected StructuredContent randomStructuredContent() throws Exception {
		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = new ContentFieldValue() {
							{
								data = RandomTestUtil.randomString(10);
							}
						};
						name = "Foo";
					}
				}
			});
		structuredContent.setContentStructureId(_ddmStructure.getStructureId());

		return structuredContent;
	}

	@Override
	protected StructuredContent
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				Long assetLibraryId, StructuredContent structuredContent)
		throws Exception {

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return structuredContentResource.postAssetLibraryStructuredContent(
			assetLibraryId, structuredContent);
	}

	@Override
	protected StructuredContent
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				Long contentStructureId, StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), structuredContent);
	}

	@Override
	protected Long
		testGetContentStructureStructuredContentsPage_getContentStructureId() {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected Long
		testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId() {

		return _irrelevantJournalFolder.getFolderId();
	}

	@Override
	protected Long
		testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId() {

		return _journalFolder.getFolderId();
	}

	@Override
	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return testPostSiteStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Override
	protected StructuredContent
			testPostAssetLibraryStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return super.testPostAssetLibraryStructuredContent_addStructuredContent(
			structuredContent);
	}

	@Override
	protected StructuredContent
			testPutAssetLibraryStructuredContentPermissionsPage_addStructuredContent()
		throws Exception {

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return structuredContentResource.postAssetLibraryStructuredContent(
			testDepotEntry.getDepotEntryId(), structuredContent);
	}

	private DDMStructure _addDDMStructure(Group group, String fileName)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read(fileName)), StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMTemplate _addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			ddmStructure.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.vm"), LocaleUtil.US);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _randomColor() {
		return String.format(
			"#%02d%02d%02d", RandomTestUtil.randomInt(0, 100),
			RandomTestUtil.randomInt(0, 100), RandomTestUtil.randomInt(0, 100));
	}

	private StructuredContent _randomCompleteStructuredContent()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(), journalFolder.getFolderId());

		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentFields(
			_randomContentFields(journalArticle));
		structuredContent.setContentStructureId(
			_ddmComplexStructure.getStructureId());

		return structuredContent;
	}

	private ContentField[] _randomContentFields(JournalArticle journalArticle) {
		return new ContentField[] {
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = RandomTestUtil.randomString(10);
						}
					};
					name = "Text";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _COMPLETE_STRUCTURED_CONTENT_OPTIONS
								[RandomTestUtil.randomInt(0, 2)];
						}
					};
					name = "SelectFromList";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _COMPLETE_STRUCTURED_CONTENT_OPTIONS
								[RandomTestUtil.randomInt(0, 2)];
						}
					};
					name = "SingleSelection";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data =
								"[" +
									_COMPLETE_STRUCTURED_CONTENT_OPTIONS
										[RandomTestUtil.randomInt(0, 2)] + "]";
						}
					};
					name = "MultipleSelection";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomGrid();
						}
					};
					name = "Grid";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomDate();
						}
					};
					name = "Date";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = String.valueOf(RandomTestUtil.randomInt());
						}
					};
					name = "Numeric";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							image = new ContentDocument() {
								{
									id = _dlFileEntry.getPrimaryKey();
								}
							};
						}
					};
					name = "Image";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = RandomTestUtil.randomString(500);
						}
					};
					name = "RichText";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							document = new ContentDocument() {
								{
									id = _dlFileEntry.getPrimaryKey();
								}
							};
						}
					};
					name = "Upload";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomColor();
						}
					};
					name = "Color";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							structuredContentLink =
								new StructuredContentLink() {
									{
										id =
											journalArticle.getResourcePrimKey();
									}
								};
						}
					};
					name = "WebContent";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							geo = new Geo() {
								{
									latitude = RandomTestUtil.randomDouble();
									longitude = RandomTestUtil.randomDouble();
								}
							};
						}
					};
					name = "Geolocation";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							link = _layout.getFriendlyURL();
						}
					};
					name = "LinkToPage";
				}
			}
		};
	}

	private String _randomDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		return simpleDateFormat.format(new Date());
	}

	private String _randomGrid() {
		return StringBundler.concat(
			"{", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[0], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			",", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[1], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			",", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[2], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			"}");
	}

	private StructuredContent _randomLocalizedStructuredContent()
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		ContentFieldValue randomEnglishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};
		ContentFieldValue randomSpanishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = randomEnglishContentFieldValue;
						contentFieldValue_i18n = HashMapBuilder.put(
							"en-US", randomEnglishContentFieldValue
						).put(
							"es-ES", randomSpanishContentFieldValue
						).build();
						name = "MyText";
					}
				}
			});

		structuredContent.setContentStructureId(
			_ddmLocalizedStructure.getStructureId());

		return structuredContent;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static final String[] _COMPLETE_STRUCTURED_CONTENT_OPTIONS = {
		"Option1", "Option2", "Option3"
	};

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private DDMStructure _ddmComplexStructure;
	private DDMStructure _ddmLocalizedStructure;
	private DDMStructure _ddmStructure;
	private DDMTemplate _ddmTemplate;
	private DDMStructure _depotDDMStructure;
	private DLFileEntry _dlFileEntry;
	private DDMStructure _irrelevantDDMStructure;
	private JournalFolder _irrelevantJournalFolder;
	private JournalFolder _journalFolder;
	private Layout _layout;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}