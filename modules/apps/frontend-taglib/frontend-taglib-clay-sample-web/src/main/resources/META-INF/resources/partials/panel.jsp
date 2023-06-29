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

<blockquote>
	<p>Toggle content visibility using Panel.</p>
</blockquote>

<h3>DEFAULT PANEL</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			displayTitle="Panel Title 1"
		>
			<div class="panel-body">Panel Content 1</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>NOT COLLAPSABLE PANEL</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			collapsable="<%= false %>"
			displayTitle="Panel Title 2"
		>
			<div class="panel-body">Panel Content 2</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>CUSTOM COLLAPSABLE CLASS</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			collapseClassNames="bg-danger text-white"
			displayTitle="Panel Title 3"
		>
			<div class="panel-body">Panel Content 3</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>ALREADY EXPANDED</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			displayTitle="Panel Title 4"
			expanded="<%= true %>"
		>
			<div class="panel-body">Panel Content 4</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>HIDDEN ICONS</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			displayTitle="Panel Title 5"
			showCollapseIcon="<%= false %>"
		>
			<div class="panel-body">Panel Content 5</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>SECONDARY STYLE</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			displayTitle="Panel Title 6"
			displayType="secondary"
		>
			<div class="panel-body">Panel Content 6</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>NESTED TAG</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel
			displayTitle="Panel Title 7"
		>
			<div class="panel-body">
				<clay:button>Panel Content 7</clay:button>
			</div>
		</clay:panel>
	</clay:col>
</clay:row>

<h3>PANEL GROUP</h3>

<clay:row
	cssClass="mb-3"
>
	<clay:col>
		<clay:panel-group>
			<clay:panel
				displayTitle="Panel Title 8A"
				displayType="secondary"
			>
				<div class="panel-body">
					<div class="panel-body">Panel Content 8A</div>
				</div>
			</clay:panel>

			<clay:panel
				displayTitle="Panel Title 8B"
				displayType="secondary"
			>
				<div class="panel-body">
					<div class="panel-body">Panel Content 8B</div>
				</div>
			</clay:panel>

			<clay:panel
				displayTitle="Panel Title 8C"
				displayType="secondary"
			>
				<div class="panel-body">
					<div class="panel-body">Panel Content 8C</div>
				</div>
			</clay:panel>
		</clay:panel-group>
	</clay:col>
</clay:row>