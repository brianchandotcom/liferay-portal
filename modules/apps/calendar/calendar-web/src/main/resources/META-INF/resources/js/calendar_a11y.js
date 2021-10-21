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

AUI.add(
	'liferay-calendar-a11y',
	(A) => {
		let template = A.CalendarBase.HEADER_TEMPLATE;

		template = template.replace('aria-role="heading"', '');
		template = template.replace('<div class="yui3-u', '<h1 class="yui3-u');
		template = template.replace('</div> +', '</h1> +');

		A.CalendarBase.HEADER_TEMPLATE = template;
	},
	'',
	{
		requires: ['calendar'],
	}
);
