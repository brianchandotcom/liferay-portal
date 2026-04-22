<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<h3>CHECKBOX</h3>

<blockquote>
	<p>A checkbox is a component that allows the user selecting something written in its associated text label. A list of consecutive checkboxes would allow the user to select multiple things.</p>
</blockquote>

<table class="table">
	<thead>
		<tr>
			<th>STATE</th>
			<th>DEFINITION</th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxOnLabel" checked="<%= true %>" name="checkboxOn" /></td>
			<td id="checkboxOnLabel">On</td>
		</tr>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxOffLabel" name="checkboxOff" /></td>
			<td id="checkboxOffLabel">Off</td>
		</tr>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxCustomClassLabel" checked="<%= true %>" cssClass="custom-css-class" data-qa-id="clayCustomCheckbox01" name="checkboxCustomClass" /></td>
			<td id="checkboxCustomClassLabel">With custom class and id</td>
		</tr>
		<tr>
			<td><clay:checkbox label="Checkbox with Label" name="checkboxWithLabel" /></td>
			<td>With Label</td>
		</tr>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxOnDisabledLabel" checked="<%= true %>" disabled="<%= true %>" name="checkboxOnDisabled" /></td>
			<td id="checkboxOnDisabledLabel">On disabled</td>
		</tr>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxOffDisabledLabel" disabled="<%= true %>" name="checkboxOffDisabled" /></td>
			<td id="checkboxOffDisabledLabel">Off disabled</td>
		</tr>
		<tr>
			<td><clay:checkbox aria-labelledby="checkboxIndeterminateLabel" indeterminate="<%= true %>" name="checkboxIndeterminate" /></td>
			<td id="checkboxIndeterminateLabel">Indeterminate</td>
		</tr>
	</tbody>
</table>

<blockquote>
	<p>Demonstrate a checkbox with an indeterminate state in the TreeView component.</p>
</blockquote>

<div>
	<react:component
		module="{ClaySampleTreeViewWithCheckbox} from frontend-taglib-clay-sample-web"
	/>
</div>

<h3>RADIO</h3>

<blockquote>
	<p>A radio button is a component that allows the user selecting something written in its associated text label. A list of consecutive radio buttons would allow the user to select just one thing.</p>
</blockquote>

<table class="table">
	<thead>
		<tr>
			<th>STATE</th>
			<th>DEFINITION</th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td><clay:radio checked="<%= true %>" label="My Input" name="name" showLabel="<%= false %>" /></td>
			<td>On</td>
		</tr>
		<tr>
			<td><clay:radio label="My Input" name="name" showLabel="<%= false %>" /></td>
			<td>Off</td>
		</tr>
		<tr>
			<td><clay:radio checked="<%= true %>" disabled="<%= true %>" label="My Input" name="name" showLabel="<%= false %>" /></td>
			<td>On disabled</td>
		</tr>
		<tr>
			<td><clay:radio disabled="<%= true %>" label="My Input" name="name" showLabel="<%= false %>" /></td>
			<td>Off disabled</td>
		</tr>
	</tbody>
</table>

<h3>SELECTOR</h3>

<blockquote>
	<p>Selectors are frequently used as a part of forms. This elements are used when we need to select one or more within several options. These options are displayed in the button once selected.</p>
</blockquote>

<%
List<SelectOption> selectOptions = new ArrayList<>();

for (int i = 0; i < 8; i++) {
	selectOptions.add(new SelectOption("Sample " + i, String.valueOf(i)));
}
%>

<clay:select
	containerCssClass="custom-container-css-class"
	cssClass="custom-css-class"
	id="pe0mdaf1n"
	label="Regular Select Element"
	name="name"
	options="<%= selectOptions %>"
/>

<clay:select
	disabled="<%= true %>"
	id="6e0paj9ij"
	label="Disabled Regular Select Element"
	name="name"
	options="<%= selectOptions %>"
/>

<clay:select
	id="lb6e0l8fq"
	label="Multiple Select Element"
	multiple="<%= true %>"
	name="name"
	options="<%= selectOptions %>"
/>

<clay:select
	disabled="<%= true %>"
	id="c8fs6qlrj"
	label="Disabled Multiple Select Element"
	multiple="<%= true %>"
	name="name"
	options="<%= selectOptions %>"
/>

<h3>MULTISELECT</h3>

<clay:multiselect
	helpText="Help text is displayed here."
	id="multiselect-1"
	inputName="multiSelectInput1"
	label="Multiselect 1"
	multiselectLocator="<%= multiselectDisplayContext.getMultiselectLocator() %>"
	selectedMultiselectItems="<%= multiselectDisplayContext.getSelectedMultiselectItemsWithCustomProperties() %>"
	sourceMultiselectItems="<%= multiselectDisplayContext.getSourceMultiselectItemsWithCustomProperties() %>"
/>

<clay:multiselect
	helpText="Help text is displayed here."
	id="multiselect-2"
	inputName="multiSelectInput2"
	label="Multiselect with Custom Menu Renderer"
	propsTransformer="{ClaySampleMultiselectPropsTransformer} from frontend-taglib-clay-sample-web"
	selectedMultiselectItems="<%= multiselectDisplayContext.getSelectedMultiselectItems() %>"
	sourceMultiselectItems="<%= multiselectDisplayContext.getSourceMultiselectItems() %>"
/>

<h3>FORM GROUP</h3>

<blockquote>
	<p>Form group variants: default and small. Labels and controls should scale proportionally with the variant.</p>
</blockquote>

<div class="form-group" id="formGroupDefault">
	<label class="control-label" for="inputDefault">Default Input</label>

	<input class="form-control" id="inputDefault" type="text" />
</div>

<div class="form-group form-group-sm" id="formGroupSm">
	<label class="control-label" for="inputSm">Small Input</label>

	<input class="form-control form-control-sm" id="inputSm" type="text" />
</div>

<div class="form-group" id="formGroupDefaultTextarea">
	<label class="control-label" for="textareaDefault">Default Textarea</label>

	<textarea class="form-control" id="textareaDefault"></textarea>
</div>

<div class="form-group form-group-sm" id="formGroupSmTextarea">
	<label class="control-label" for="textareaSm">Small Textarea</label>

	<textarea class="form-control form-control-sm" id="textareaSm"></textarea>
</div>

<div class="form-group" id="formGroupDefaultSelect">
	<label class="control-label" for="selectDefault">Default Select</label>

	<select class="form-control" id="selectDefault">
		<option>Option 1</option>
		<option>Option 2</option>
	</select>
</div>

<div class="form-group form-group-sm" id="formGroupSmSelect">
	<label class="control-label" for="selectSm">Small Select</label>

	<select class="form-control form-control-sm" id="selectSm">
		<option>Option 1</option>
		<option>Option 2</option>
	</select>
</div>