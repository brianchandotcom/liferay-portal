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
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllWidth" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="borderWidthTop" />

				<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-top-unit" title="top-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="borderWidthRight" />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthRightUnit" title="right-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="borderWidthBottom" />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthBottomUnit" title="bottom-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="borderWidthLeft" />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthLeftUnit" title="left-border-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllStyle" type="toggle-switch" />

			<aui:select label="top" name="borderStyleTop" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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

			<aui:select label="right" name="borderStyleRight" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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

			<aui:select label="bottom" name="borderStyleBottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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

			<aui:select label="left" name="borderStyleLeft" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllColor" type="toggle-switch" />

			<aui:input label="top" name="borderColorTop" wrapperCssClass="field-row" />

			<aui:input label="right" name="borderColorRight" wrapperCssClass="field-row" />

			<aui:input label="bottom" name="borderColorBottom" wrapperCssClass="field-row" />

			<aui:input label="left" name="borderColorLeft" wrapperCssClass="field-row" />
		</aui:fieldset>
	</aui:col>
</aui:row>