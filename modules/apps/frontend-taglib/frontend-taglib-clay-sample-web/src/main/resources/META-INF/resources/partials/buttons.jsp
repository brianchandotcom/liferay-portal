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
	<p>Buttons communicate an action to happen on user interaction.</p>
</blockquote>

<h3>TYPES</h3>

<table class="table">
	<thead>
		<tr>
			<th>TYPE</th>
			<th>USAGE</th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td>
				<div class="flex-md-nowrap mb-2 row">
					<div class="col"><clay:button label="Primary" /></div>
					<div class="col"><clay:button ariaLabel="Workflow" icon="workflow" /></div>
				</div>

				<div class="flex-md-nowrap row">
					<div class="col"><clay:button disabled="<%= true %>" label="Primary" /></div>
					<div class="col"><clay:button ariaLabel="Workflow" disabled="<%= true %>" icon="workflow" /></div>
				</div>
			</td>
			<td>
				<strong>Primary</strong>: The primary button is always use for the most important actions. There can't be two primary actions together or near by.
			</td>
		</tr>
		<tr>
			<td>
				<div class="flex-md-nowrap mb-2 row">
					<div class="col"><clay:button label="Secondary" style="secondary" /></div>
					<div class="col"><clay:button ariaLabel="Wiki" icon="wiki" style="secondary" /></div>
				</div>

				<div class="flex-md-nowrap row">
					<div class="col"><clay:button disabled="<%= true %>" label="Secondary" style="secondary" /></div>
					<div class="col"><clay:button ariaLabel="Wiki" disabled="<%= true %>" icon="wiki" style="secondary" /></div>
				</div>
			</td>
			<td>
				<strong>Secondary</strong>: The secondary button is always use for the secondary actions. There can be several secondary actions near by.
			</td>
		</tr>
		<tr>
			<td>
				<div class="flex-md-nowrap mb-2 row">
					<div class="col"><clay:button label="Borderless" style="borderless" /></div>
					<div class="col"><clay:button ariaLabel="Page Template" icon="page-template" style="borderless" /></div>
				</div>

				<div class="flex-md-nowrap row">
					<div class="col"><clay:button disabled="<%= true %>" label="Borderless" style="borderless" /></div>
					<div class="col"><clay:button ariaLabel="Page Template" disabled="<%= true %>" icon="page-template" style="borderless" /></div>
				</div>
			</td>
			<td>
				<strong>Borderless</strong>: Use in those cases as toolbars where the secondary button would be too heavy for the pattern design. In this way the design gets cleaner.
			</td>
		</tr>
		<tr>
			<td>
				<div class="flex-md-nowrap mb-2 row">
					<div class="col"><clay:button label="Link" style="link" /></div>
					<div class="col"><clay:button ariaLabel="Add Role" icon="add-role" style="link" /></div>
				</div>

				<div class="flex-md-nowrap row">
					<div class="col"><clay:button disabled="<%= true %>" label="Link" style="link" /></div>
					<div class="col"><clay:button ariaLabel="Add Role" disabled="<%= true %>" icon="add-role" style="link" /></div>
				</div>
			</td>
			<td>
				<strong>Link</strong>: Used for many Cancel actions.
			</td>
		</tr>
	</tbody>
</table>

<h3>VARIATIONS</h3>

<div class="row text-center">
		<div class="col-md-2">
			<clay:button
				icon="share"
				label="Share"
			/>

			<div>Icon and Text Button</div>
		</div>

		<div class="col-md-2">
			<clay:button
				icon="indent-less"
				monospaced="<%= true %>"
				style="secondary"
			/>

			<div>Monospaced Button</div>
		</div>

		<div class="col-md-4">
			<clay:button
				block="<%= true %>"
				label="Button"
			/>

			<div>Block Level Button</div>
		</div>

		<div class="col-md-2">
			<clay:button
				icon="plus"
				monospaced="<%= true %>"
				style="secondary"
			/>

			<div>Plus Button</div>
		</div>

		<div class="col-md-2">
			<clay:button
				icon="ellipsis-v"
				monospaced="<%= true %>"
				style="borderless"
			/>

			<div>Action Button</div>
		</div>
	</div>