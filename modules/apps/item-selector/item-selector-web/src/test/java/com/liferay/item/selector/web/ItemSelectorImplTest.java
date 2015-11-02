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

package com.liferay.item.selector.web;

import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.web.util.ItemSelectorCriterionSerializer;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactory;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class ItemSelectorImplTest extends PowerMockito {

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_itemSelectorImpl = new ItemSelectorImpl();

		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testFileEntryItemSelectorReturnType);
		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testStringItemSelectorReturnType);
		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testURLItemSelectorReturnType);

		_itemSelectorImpl.setItemSelectorCriterionSerializer(
			_itemSelectorCriterionSerializer);

		_mediaItemSelectorCriterion = new MediaItemSelectorCriterion();

		_mediaItemSelectorCriterion.setFileExtension("jpg");
		_mediaItemSelectorCriterion.setMaxSize(2048);

		desiredItemSelectorReturnTypes = new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new TestFileEntryItemSelectorReturnType());
		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_mediaItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		Portal portal = mock(Portal.class);

		LiferayPortletResponse liferayPortletResponse =
			mock(LiferayPortletResponse.class);

		LiferayPortletRequest liferayPortletRequest =
			getMockPortletRequest(liferayPortletResponse);

		when(
			portal.getLiferayPortletResponse(Mockito.any(PortletResponse.class))
		).thenReturn(
			liferayPortletResponse
		);

		when(
			portal.getLiferayPortletRequest(Mockito.any(PortletRequest.class))
		).thenReturn(
			liferayPortletRequest
		);

		Layout layout = mock(Layout.class);

		when(
			portal.getControlPanelLayout(
				Mockito.anyLong(), Mockito.any(Group.class))
		).thenReturn(
			layout
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);

		PortletURLFactory portletURLFactory = mock(PortletURLFactory.class);

		LiferayPortletURL mockLiferayPortletURL = mock(LiferayPortletURL.class);

		when(
			portletURLFactory.create(
				Mockito.any(HttpServletRequest.class), Mockito.anyString(),
				Mockito.any(Layout.class), Mockito.anyString())
		).thenReturn(
			mockLiferayPortletURL
		);

		PortletURLFactoryUtil portletURLFactoryUtil =
			new PortletURLFactoryUtil();

		portletURLFactoryUtil.setPortletURLFactory(portletURLFactory);
	}

	@Test
	public void testGetItemSelectorParameters() {
		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedEventName", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		Assert.assertEquals(
			"itemSelectedEventName",
			parameters.get(
				ItemSelectorImpl.PARAMETER_ITEM_SELECTED_EVENT_NAME)[0]);
		Assert.assertEquals(
			MediaItemSelectorCriterion.class.getName() + "," +
				FlickrItemSelectorCriterion.class.getName(),
			parameters.get(ItemSelectorImpl.PARAMETER_CRITERIA)[0]);
		Assert.assertNull(parameters.get("0_desiredItemSelectorReturnTypes"));
		Assert.assertNotNull(parameters.get("0_json")[0]);
		Assert.assertNotNull(parameters.get("1_json")[0]);

		Assert.assertEquals(4, parameters.size());
	}

	@Test
	public void testGetItemSelectorRendering() {
		setUpItemSelectionCriterionHandlers();

		PortletResponse portletResponse = mock(PortletResponse.class);

		PortletRequest portletRequest = getMockPortletRequest(portletResponse);

		ItemSelectorRendering itemSelectorRendering =
			_itemSelectorImpl.getItemSelectorRendering(
				portletRequest, portletResponse);

		Assert.assertEquals(
			"itemSelectedEventName",
			itemSelectorRendering.getItemSelectedEventName());

		List<ItemSelectorViewRenderer> itemSelectorViewRenderers =
			itemSelectorRendering.getItemSelectorViewRenderers();

		ItemSelectorViewRenderer mediaItemSelectorViewRenderer =
			itemSelectorViewRenderers.get(0);

		MediaItemSelectorCriterion mediaItemSelectorCriterion =
			(MediaItemSelectorCriterion)
				mediaItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_mediaItemSelectorCriterion.getFileExtension(),
			mediaItemSelectorCriterion.getFileExtension());
		Assert.assertEquals(
			_mediaItemSelectorCriterion.getMaxSize(),
			mediaItemSelectorCriterion.getMaxSize());
		Assert.assertTrue(
			(ItemSelectorView<?>)
				mediaItemSelectorViewRenderer.getItemSelectorView()
					instanceof MediaItemSelectorView);

		ItemSelectorViewRenderer flickrItemSelectorViewRenderer =
			itemSelectorViewRenderers.get(1);

		FlickrItemSelectorCriterion flickrItemSelectorCriterion =
			(FlickrItemSelectorCriterion)
				flickrItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_flickrItemSelectorCriterion.getUser(),
			flickrItemSelectorCriterion.getUser());
		Assert.assertTrue(
			(ItemSelectorView<?>)
				flickrItemSelectorViewRenderer.getItemSelectorView()
					instanceof FlickrItemSelectorView);
		Assert.assertEquals(2, itemSelectorViewRenderers.size());
	}

	protected LiferayPortletRequest getMockPortletRequest(
		PortletResponse portletResponse) {

		LiferayPortletRequest liferayPortletRequest = mock(
			LiferayPortletRequest.class);

		when(
			liferayPortletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE)
		).thenReturn(
			portletResponse
		);

		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		when(
			liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedEventName", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		when(
			liferayPortletRequest.getParameterMap()
		).thenReturn(
			parameters
		);

		HttpServletRequest request = mock(HttpServletRequest.class);

		when(
			liferayPortletRequest.getHttpServletRequest()
		).thenReturn(
			request
		);

		when(
			request.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Group group = mock(Group.class);

		when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		when(
			group.getGroupId()
		).thenReturn(
			12345L
		);

		when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			12345L
		);

		when(
			themeDisplay.getCompanyId()
		).thenReturn(
			12345L
		);

		return liferayPortletRequest;
	}

	protected void setUpItemSelectionCriterionHandlers() {
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new FlickrItemSelectorCriterionHandler());
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new MediaItemSelectorCriterionHandler());
	}

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private final ItemSelectorCriterionSerializer<FlickrItemSelectorCriterion>
		_itemSelectorCriterionSerializer =
			new ItemSelectorCriterionSerializer();
	private ItemSelectorImpl _itemSelectorImpl;
	private MediaItemSelectorCriterion _mediaItemSelectorCriterion;
	private final ItemSelectorReturnType _testFileEntryItemSelectorReturnType =
		new TestFileEntryItemSelectorReturnType();
	private final ItemSelectorReturnType _testStringItemSelectorReturnType =
		new TestStringItemSelectorReturnType();
	private final ItemSelectorReturnType _testURLItemSelectorReturnType =
		new TestURLItemSelectorReturnType();

}