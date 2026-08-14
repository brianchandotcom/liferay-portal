/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.pim.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.pim.client.dto.v1_0.Link;
import com.liferay.headless.pim.client.dto.v1_0.LinkReference;
import com.liferay.headless.pim.client.pagination.Page;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.pim.site.initializer.constants.PIMObjectDefinitionConstants;
import com.liferay.site.pim.site.initializer.link.PIMLinkType;
import com.liferay.site.pim.site.initializer.test.util.PIMBaseSKUTestUtil;
import com.liferay.site.pim.site.initializer.test.util.PIMTestUtil;
import com.liferay.site.pim.site.initializer.test.util.link.TestPIMLinkType;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
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
 * @author Stefano Motta
 */
@FeatureFlag("LPD-96666")
@RunWith(Arquillian.class)
public class LinkResourceTest extends BaseLinkResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		PIMTestUtil.getOrAddGroup();

		Bundle bundle = FrameworkUtil.getBundle(LinkResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			PIMLinkType.class, new TestPIMLinkType(), null);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_serviceRegistration.unregister();
	}

	@Override
	@Test
	public void testDeleteScopeScopeKeyLink() throws Exception {
		DepotEntry depotEntry = PIMTestUtil.addSpaceDepotEntry();

		ObjectEntry objectEntry1 = PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
			depotEntry.getGroupId());
		ObjectEntry objectEntry2 = PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
			depotEntry.getGroupId());

		linkResource.postScopeScopeKeyLink(
			String.valueOf(depotEntry.getGroupId()),
			_toLink(
				_TYPE, objectEntry1, Collections.singletonList(objectEntry2)));

		Assert.assertNotNull(
			_getClusterKey(
				depotEntry.getGroupId(),
				objectEntry2.getExternalReferenceCode()));

		linkResource.deleteScopeScopeKeyLink(
			String.valueOf(depotEntry.getGroupId()),
			objectEntry2.getModelClassName(),
			objectEntry2.getExternalReferenceCode(), _TYPE);

		Assert.assertNotNull(
			_getClusterKey(
				depotEntry.getGroupId(),
				objectEntry1.getExternalReferenceCode()));
		Assert.assertNull(
			_getClusterKey(
				depotEntry.getGroupId(),
				objectEntry2.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetScopeScopeKeyLinksPage() throws Exception {
		DepotEntry depotEntry = PIMTestUtil.addSpaceDepotEntry();

		ObjectEntry sourceObjectEntry =
			PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
				depotEntry.getGroupId());

		Page<Link> page = _getLinksPage(sourceObjectEntry, _TYPE);

		Assert.assertEquals(0, page.getTotalCount());

		ObjectEntry targetObjectEntry1 =
			PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
				depotEntry.getGroupId());
		ObjectEntry targetObjectEntry2 =
			PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
				depotEntry.getGroupId());

		linkResource.postScopeScopeKeyLink(
			String.valueOf(depotEntry.getGroupId()),
			_toLink(
				_TYPE, sourceObjectEntry,
				Arrays.asList(targetObjectEntry1, targetObjectEntry2)));

		page = _getLinksPage(sourceObjectEntry, _TYPE);

		Assert.assertEquals(1, page.getTotalCount());

		Link link = page.fetchFirstItem();

		Assert.assertEquals(_TYPE, link.getType());

		LinkReference sourceLinkReference = link.getSourceLinkReference();

		Assert.assertEquals(
			sourceObjectEntry.getExternalReferenceCode(),
			sourceLinkReference.getExternalReferenceCode());
		Assert.assertEquals("approved", sourceLinkReference.getStatus());

		Assert.assertEquals(2, link.getTargetLinkReferences().length);

		List<Link> links = (List<Link>)page.getItems();

		Assert.assertFalse(_containsLink(links, sourceObjectEntry, _TYPE));
		Assert.assertTrue(_containsLink(links, targetObjectEntry1, _TYPE));
		Assert.assertTrue(_containsLink(links, targetObjectEntry2, _TYPE));

		_objectEntryLocalService.moveObjectEntryToTrash(
			TestPropsValues.getUserId(), targetObjectEntry2,
			ServiceContextTestUtil.getServiceContext(depotEntry.getGroupId()));

		page = _getLinksPage(sourceObjectEntry, _TYPE);

		link = page.fetchFirstItem();

		Assert.assertEquals(1, link.getTargetLinkReferences().length);

		links = (List<Link>)page.getItems();

		Assert.assertTrue(_containsLink(links, targetObjectEntry1, _TYPE));
		Assert.assertFalse(_containsLink(links, targetObjectEntry2, _TYPE));

		ObjectEntry targetObjectEntry3 =
			PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
				depotEntry.getGroupId());

		linkResource.postScopeScopeKeyLink(
			String.valueOf(depotEntry.getGroupId()),
			_toLink(
				TestPIMLinkType.TYPE, sourceObjectEntry,
				Collections.singletonList(targetObjectEntry3)));

		page = _getLinksPage(sourceObjectEntry, _TYPE);

		Assert.assertEquals(1, page.getTotalCount());

		links = (List<Link>)page.getItems();

		Assert.assertTrue(_containsLink(links, targetObjectEntry1, _TYPE));
		Assert.assertFalse(_containsLink(links, targetObjectEntry3, _TYPE));

		page = _getLinksPage(sourceObjectEntry, null);

		Assert.assertEquals(2, page.getTotalCount());

		links = (List<Link>)page.getItems();

		Assert.assertTrue(_containsLink(links, targetObjectEntry1, _TYPE));
		Assert.assertTrue(
			_containsLink(links, targetObjectEntry3, TestPIMLinkType.TYPE));
	}

	@Override
	@Test
	public void testPostScopeScopeKeyLink() throws Exception {
		DepotEntry depotEntry = PIMTestUtil.addSpaceDepotEntry();

		ObjectEntry objectEntry1 = PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
			depotEntry.getGroupId());
		ObjectEntry objectEntry2 = PIMBaseSKUTestUtil.addPIMBaseSKUObjectEntry(
			depotEntry.getGroupId());

		linkResource.postScopeScopeKeyLink(
			String.valueOf(depotEntry.getGroupId()),
			_toLink(
				_TYPE, objectEntry1, Collections.singletonList(objectEntry2)));

		String clusterKey = _getClusterKey(
			depotEntry.getGroupId(), objectEntry1.getExternalReferenceCode());

		Assert.assertNotNull(clusterKey);
		Assert.assertEquals(
			clusterKey,
			_getClusterKey(
				depotEntry.getGroupId(),
				objectEntry2.getExternalReferenceCode()));
	}

	private boolean _containsLink(
		List<Link> links, ObjectEntry objectEntry, String type) {

		if (ListUtil.exists(
				links,
				link ->
					Objects.equals(link.getType(), type) &&
					ListUtil.exists(
						ListUtil.fromArray(link.getTargetLinkReferences()),
						linkReference -> Objects.equals(
							linkReference.getExternalReferenceCode(),
							objectEntry.getExternalReferenceCode())))) {

			return true;
		}

		return false;
	}

	private String _getClusterKey(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					PIMObjectDefinitionConstants.EXTERNAL_REFERENCE_CODE_LINK,
					TestPropsValues.getCompanyId());

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				groupId, TestPropsValues.getCompanyId(), 0,
				objectDefinition.getObjectDefinitionId(), null, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Map<String, Serializable> values : valuesList) {
			if (Objects.equals(
					externalReferenceCode,
					MapUtil.getString(
						values, "sourceClassExternalReferenceCode")) &&
				Objects.equals(_TYPE, MapUtil.getString(values, "type"))) {

				return MapUtil.getString(values, "clusterKey");
			}
		}

		return null;
	}

	private Page<Link> _getLinksPage(ObjectEntry objectEntry, String type)
		throws Exception {

		return linkResource.getScopeScopeKeyLinksPage(
			String.valueOf(objectEntry.getGroupId()),
			objectEntry.getModelClassName(),
			objectEntry.getExternalReferenceCode(), type);
	}

	private Link _toLink(
		String linkType, ObjectEntry sourceObjectEntry,
		List<ObjectEntry> targetObjectEntries) {

		return new Link() {
			{
				setSourceLinkReference(_toLinkReference(sourceObjectEntry));
				setTargetLinkReferences(
					TransformUtil.transformToArray(
						targetObjectEntries,
						LinkResourceTest.this::_toLinkReference,
						LinkReference.class));
				setType(() -> linkType);
			}
		};
	}

	private LinkReference _toLinkReference(ObjectEntry objectEntry) {
		return new LinkReference() {
			{
				setClassName(objectEntry.getModelClassName());
				setExternalReferenceCode(
					objectEntry.getExternalReferenceCode());
			}
		};
	}

	private static final String _TYPE = "variant";

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ServiceRegistration<PIMLinkType> _serviceRegistration;

}