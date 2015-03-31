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

package com.liferay.document.selector.impl;

import com.liferay.document.selector.ItemSelectorRendering;
import com.liferay.document.selector.ItemSelectorViewRenderer;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class ItemSelectorImplTest extends PowerMockito {

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		_flickrItemSelectorCriterion.setDesiredReturnTypes(URL.class);

		_itemSelectorImpl = new ItemSelectorImpl();

		_mediaItemSelectorCriterion = new MediaItemSelectorCriterion();

		_mediaItemSelectorCriterion.setFileExtension("jpg");
		_mediaItemSelectorCriterion.setMaxSize(2048);
	}

	@Test
	public void testGetItemSelectorParameters() {
		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedCallback", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		Assert.assertEquals(
			"itemSelectedCallback",
			parameters.get(ItemSelectorImpl.PARAMETER_ITEM_SELECTED_CALLBACK)[0]
		);

		Assert.assertEquals(
			MediaItemSelectorCriterion.class.getName() + "," +
				FlickrItemSelectorCriterion.class.getName(),
			parameters.get(ItemSelectorImpl.PARAMETER_CRITERIA)[0]);
		Assert.assertNull(parameters.get("0_desiredReturnTypes"));
		Assert.assertEquals(
			String.valueOf(_mediaItemSelectorCriterion.getMaxSize()),
			parameters.get("0_maxSize")[0]);
		Assert.assertEquals(
			_mediaItemSelectorCriterion.getFileExtension(),
			parameters.get("0_fileExtension")[0]);
		Assert.assertEquals(
			URL.class.getName(), parameters.get("1_desiredReturnTypes")[0]);
		Assert.assertEquals(
			_flickrItemSelectorCriterion.getUser(),
			parameters.get("1_user")[0]);

		Assert.assertEquals(6, parameters.size());
	}

	@Test
	public void testGetItemSelectorRendering() throws IOException {
		_setUpItemSelectionCriterionHandlers();

		Map<String, String[]> parameters =
			_itemSelectorImpl.getItemSelectorParameters(
				"itemSelectedCallback", _mediaItemSelectorCriterion,
				_flickrItemSelectorCriterion);

		ItemSelectorRendering itemSelectorRendering =
			_itemSelectorImpl.getItemSelectorRendering(parameters);

		Assert.assertEquals(
			"itemSelectedCallback",
			itemSelectorRendering.getItemSelectedCallback());

		List<ItemSelectorViewRenderer<?>> itemSelectorViewRenderers =
			itemSelectorRendering.getItemSelectorViewRenderers();

		ItemSelectorViewRenderer<MediaItemSelectorCriterion>
			mediaItemSelectorViewRenderer =
				(ItemSelectorViewRenderer<MediaItemSelectorCriterion>)
					itemSelectorViewRenderers.get(0);

		MediaItemSelectorCriterion mediaItemSelectorCriterion =
			mediaItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_mediaItemSelectorCriterion.getFileExtension(),
			mediaItemSelectorCriterion.getFileExtension());
		Assert.assertEquals(
			_mediaItemSelectorCriterion.getMaxSize(),
			mediaItemSelectorCriterion.getMaxSize());
		Assert.assertTrue(
			mediaItemSelectorViewRenderer.getItemSelectorView()
				instanceof MediaItemSelectorView);

		ItemSelectorViewRenderer<FlickrItemSelectorCriterion>
			flickrItemSelectorViewRenderer =
				(ItemSelectorViewRenderer<FlickrItemSelectorCriterion>)
					itemSelectorViewRenderers.get(1);

		FlickrItemSelectorCriterion flickrItemSelectorCriterion =
			flickrItemSelectorViewRenderer.getItemSelectorCriterion();

		Assert.assertEquals(
			_flickrItemSelectorCriterion.getUser(),
			flickrItemSelectorCriterion.getUser());
		Assert.assertTrue(
			flickrItemSelectorViewRenderer.getItemSelectorView()
				instanceof FlickrItemSelectorView);

		Assert.assertEquals(2, itemSelectorViewRenderers.size());
	}

	private void _setUpItemSelectionCriterionHandlers() {
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new MediaItemSelectorCriterionHandler());
		_itemSelectorImpl.setItemSelectionCriterionHandler(
			new FlickrItemSelectorCriterionHandler());
	}

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private ItemSelectorImpl _itemSelectorImpl;
	private MediaItemSelectorCriterion _mediaItemSelectorCriterion;

}