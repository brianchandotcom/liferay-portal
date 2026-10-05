/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.webdav.DDMWebDAV;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.webdav.WebDAVRequestImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class DDMWebDAVImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(
			_group1, RoleConstants.SITE_ADMINISTRATOR);
	}

	@Test
	public void testGetResource() throws Exception {
		DDMStructure ddmStructure1 = _addStructure(_group1);
		DDMStructure ddmStructure2 = _addStructure(_group2);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user)) {

			_assertGetResource(
				ddmStructure1, _group1, DDMWebDAV.TYPE_STRUCTURES,
				ddmStructure1.getStructureId());

			AssertUtils.assertFailure(
				WebDAVException.class,
				StringBundler.concat(
					PrincipalException.MustHavePermission.class.getName(),
					": User ", _user.getUserId(), " must have ",
					ActionKeys.VIEW, " permission for ",
					DDMStructure.class.getName(), StringPool.DASH,
					JournalArticle.class.getName(), StringPool.SPACE,
					ddmStructure2.getStructureId()),
				() -> _getResource(
					_group1, DDMWebDAV.TYPE_STRUCTURES,
					ddmStructure2.getStructureId()));
		}

		DDMTemplate ddmTemplate1 = _addTemplate(ddmStructure1, _group1);
		DDMTemplate ddmTemplate2 = _addTemplate(ddmStructure2, _group2);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user)) {

			_assertGetResource(
				ddmTemplate1, _group1, DDMWebDAV.TYPE_TEMPLATES,
				ddmTemplate1.getTemplateId());

			AssertUtils.assertFailure(
				WebDAVException.class,
				StringBundler.concat(
					PrincipalException.MustHavePermission.class.getName(),
					": User ", _user.getUserId(), " must have ",
					ActionKeys.VIEW, " permission for ",
					DDMTemplate.class.getName(), StringPool.DASH,
					JournalArticle.class.getName(), StringPool.SPACE,
					ddmTemplate2.getTemplateId()),
				() -> _getResource(
					_group1, DDMWebDAV.TYPE_TEMPLATES,
					ddmTemplate2.getTemplateId()));
		}
	}

	private DDMStructure _addStructure(Group group) throws Exception {
		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm();

		return _ddmStructureLocalService.addStructure(
			null, TestPropsValues.getUserId(), group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			null, ddmForm, DDMUtil.getDefaultDDMFormLayout(ddmForm),
			StorageType.DEFAULT.toString(), DDMStructureConstants.TYPE_DEFAULT,
			new ServiceContext());
	}

	private DDMTemplate _addTemplate(DDMStructure ddmStructure, Group group)
		throws Exception {

		return _ddmTemplateLocalService.addTemplate(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class), null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_VM,
			DDMTemplateTestUtil.getSampleTemplateVM(), false, false, null, null,
			new ServiceContext());
	}

	private void _assertGetResource(
			BaseModel<?> expectedBaseModel, Group group, String type,
			long typeId)
		throws Exception {

		Resource resource = _getResource(group, type, typeId);

		Assert.assertEquals(expectedBaseModel, resource.getModel());
	}

	private Resource _getResource(Group group, String type, long typeId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(
			StringBundler.concat(
				group.getFriendlyURL(), "/journal/", type, "/", typeId));

		WebDAVRequest webDAVRequest = new WebDAVRequestImpl(
			null, mockHttpServletRequest, new MockHttpServletResponse(),
			RandomTestUtil.randomString(),
			PermissionThreadLocal.getPermissionChecker());

		return _ddmWebDAV.getResource(
			webDAVRequest, "/webdav", "journal",
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private DDMWebDAV _ddmWebDAV;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private User _user;

}