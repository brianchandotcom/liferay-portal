<%--
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
--%>

<%@ include file="/init.jsp" %>

<h3>DROPDOWN MENU</h3>

<blockquote><p>A dropdown is a list of options related to the element that triggers it.</p></blockquote>

<div class="row">

	<%
	List<Map<String, Object>> defaultItems = new ArrayList<>();

	for (int i = 0; i < 4; i++) {
		Map<String, Object> item = new HashMap<>();

		if (i == 1) {
			item.put("disabled", true);
		}
		else if (i == 2) {
			item.put("active", true);
		}

		item.put("label", "Option " + i);
		item.put("url", "#" + i);

		defaultItems.add(item);
	}
	%>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= defaultItems %>"
			label="Default"
		/>
	</div>

	<%
	List<Map<String, Object>> dividerItems = new ArrayList<>();

	Map<String, Object> dividerItemsGroup1 = new HashMap<>();

	dividerItems.add(dividerItemsGroup1);

	List<Map<String, Object>> dividerItemsGroup1Items = new ArrayList<>();

	dividerItemsGroup1.put("items", dividerItemsGroup1Items);
	dividerItemsGroup1.put("separator", true);
	dividerItemsGroup1.put("type", "group");

	Map<String, Object> editItem = new HashMap<>();

	editItem.put("label", "Edit");
	editItem.put("url", "#edit");

	dividerItemsGroup1Items.add(editItem);

	Map<String, Object> previewItem = new HashMap<>();

	previewItem.put("label", "Preview");
	previewItem.put("url", "#preview");

	dividerItemsGroup1Items.add(previewItem);

	Map<String, Object> dividerItemsGroup2 = new HashMap<>();

	dividerItems.add(dividerItemsGroup2);

	List<Map<String, Object>> dividerItemsGroup2Items = new ArrayList<>();

	dividerItemsGroup2.put("items", dividerItemsGroup2Items);
	dividerItemsGroup2.put("type", "group");

	Map<String, Object> expireItem = new HashMap<>();

	expireItem.put("label", "Expire");
	expireItem.put("url", "#expire");

	dividerItemsGroup2Items.add(expireItem);

	Map<String, Object> viewHistoryItem = new HashMap<>();

	viewHistoryItem.put("label", "View History");
	viewHistoryItem.put("url", "#history");

	dividerItemsGroup2Items.add(viewHistoryItem);
	%>

	<div class="col-md-2">
		<clay:dropdown-menu
			items="<%= dividerItems %>"
			label="Dividers"
		/>
	</div>

	<%
	List<Map<String, Object>> inputItems = new ArrayList<>();

	Map<String, Object> inputItemsGroup1 = new HashMap<>();

	inputItems.add(inputItemsGroup1);

	List<Map<String, Object>> inputItemsGroup1Items = new ArrayList<>();

	inputItemsGroup1.put("items", inputItemsGroup1Items);
	inputItemsGroup1.put("label", "Group 1");
	inputItemsGroup1.put("type", "group");

	for (int i = 0; i < 2; i++) {
		Map<String, Object> item = new HashMap<>();

		item.put("inputName", "checkbox");
		item.put("inputValue", "value" + i);
		item.put("label", "Option " + i);
		item.put("type", "checkbox");

		inputItemsGroup1Items.add(item);
	}

	Map<String, Object> inputItemsGroup2 = new HashMap<>();

	inputItems.add(inputItemsGroup2);

	List<Map<String, Object>> inputItemsGroup2Items = new ArrayList<>();

	inputItemsGroup2.put("inputName", "radiogroup");
	inputItemsGroup2.put("items", inputItemsGroup2Items);
	inputItemsGroup2.put("label", "Group 2");
	inputItemsGroup2.put("type", "radiogroup");

	for (int i = 0; i < 2; i++) {
		Map<String, Object> item = new HashMap<>();

		item.put("inputValue", "value" + i + 2);
		item.put("label", "Option " + i + 2);

		inputItemsGroup2Items.add(item);
	}
	%>

	<div class="col-md-2">
		<clay:dropdown-menu
			buttonLabel="Done"
			items="<%= inputItems %>"
			label="Inputs"
			searchable="<%= true %>"
		/>
	</div>

	<%
	List<Map<String, Object>> iconItems = new ArrayList<>();

	for (int i = 0; i < 4; i++) {
		Map<String, Object> item = new HashMap<>();

		if (i == 3) {
			item.put("disabled", true);
		}
		else if (i != 2) {
			item.put("indicatorSymbol", "check-circle-full");
		}

		item.put("label", "Option " + i);
		item.put("url", "#" + i);

		iconItems.add(item);
	}
	%>

	<div class="col-md-2">
		<clay:dropdown-menu
			indicatorsPosition="left"
			items="<%= iconItems %>"
			label="Icons"
		/>
	</div>

	<div class="col-md-2">
		<clay:dropdown-actions
			items="<%= defaultItems %>"

		/>
	</div>

	<%
	List<Map<String, Object>> actionItems = new ArrayList<>();

	for (int i = 0; i < 4; i++) {
		Map<String, Object> item = new HashMap<>();

		if (i == 2) {
			item.put("disabled", true);
		}

		item.put("label", "Option " + i);
		item.put("url", "#" + i);

		actionItems.add(item);
	}
	%>

	<div class="col-md-2">
		<clay:dropdown-actions
			buttonLabel="More"
			buttonStyle="secondary"
			caption="Showing 4 of 32 Options"
			helpText="You can customize this menu or see all you have by pressing \"more\"."
			items="<%= actionItems %>"

		/>
	</div>
</div>