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

<aui:row>
	<aui:col cssClass="lfr-border-width use-for-all-column" width="<%= 33 %>">
		<aui:fieldset label="border-width">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-width" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="lfr-border-width-top" />

				<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-top-unit" title="top-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="lfr-border-width-right" />

				<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-right-unit" title="right-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="lfr-border-width-bottom" />

				<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-bottom-unit" title="bottom-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="lfr-border-width-left" />

				<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-left-unit" title="left-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="lfr-use-for-all-style" type="toggle-switch" />

			<aui:select label="top" name="lfr-border-style-top" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" />
				<aui:option label="double" />
				<aui:option label="dotted" />
				<aui:option label="groove" />
				<aui:option label="hidden[css]" value="hidden" />
				<aui:option label="inset" />
				<aui:option label="outset" />
				<aui:option label="ridge" />
				<aui:option label="solid" />
			</aui:select>

			<aui:select label="right" name="lfr-border-style-right" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" />
				<aui:option label="double" />
				<aui:option label="dotted" />
				<aui:option label="groove" />
				<aui:option label="hidden[css]" value="hidden" />
				<aui:option label="inset" />
				<aui:option label="outset" />
				<aui:option label="ridge" />
				<aui:option label="solid" />
			</aui:select>

			<aui:select label="bottom" name="lfr-border-style-bottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" />
				<aui:option label="double" />
				<aui:option label="dotted" />
				<aui:option label="groove" />
				<aui:option label="hidden[css]" value="hidden" />
				<aui:option label="inset" />
				<aui:option label="outset" />
				<aui:option label="ridge" />
				<aui:option label="solid" />
			</aui:select>

			<aui:select label="left" name="lfr-border-style-left" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" />
				<aui:option label="double" />
				<aui:option label="dotted" />
				<aui:option label="groove" />
				<aui:option label="hidden[css]" value="hidden" />
				<aui:option label="inset" />
				<aui:option label="outset" />
				<aui:option label="ridge" />
				<aui:option label="solid" />
			</aui:select>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-color" last="<%= true %>" width="<%= 33 %>">
		<aui:fieldset label="border-color">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="lfr-use-for-all-color" type="toggle-switch" />

			<aui:input label="top" name="lfr-border-color-top" wrapperCssClass="field-row" />

			<aui:input label="right" name="lfr-border-color-right" wrapperCssClass="field-row" />

			<aui:input label="bottom" name="lfr-border-color-bottom" wrapperCssClass="field-row" />

			<aui:input label="left" name="lfr-border-color-left" wrapperCssClass="field-row" />
		</aui:fieldset>
	</aui:col>
</aui:row>