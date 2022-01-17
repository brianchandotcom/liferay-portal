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

import ClayButton from '@clayui/button';
import moment from 'moment/min/moment-with-locales';
import React, {useContext} from 'react';

import {removeEmptyValues} from '../../utils/data';
import Color from '../color/Color';
import {SidebarContext} from '../sidebar/SidebarContext';

export default function List({data, field, summary, totalEntries, type}) {
	const {portletNamespace, toggleSidebar} = useContext(SidebarContext);

	const formatDate = (field, isDateTime) => {
		const locale = themeDisplay.getLanguageId().split('_', 1).join('');
		const date = moment(field).locale(locale).format('L');

		if (!isDateTime) {
			return date;
		}

		const time = moment(field).locale(locale).format('LT');

		return `${date} ${time}`;
	};

	const checkType = (field, type) => {
		switch (type) {
			case 'color':
				return <Color hexColor={field} />;
			case 'date':
				return formatDate(field);
			case 'date_time':
				return formatDate(field, true);
			default:
				return field;
		}
	};

	data = removeEmptyValues(data);

	return (
		<div className="field-list">
			<ul className="entries-list">
				{Array.isArray(data) &&
					data.map((field, index) => (
						<li key={index}>{checkType(field, type)}</li>
					))}

				{data.length === 5 && totalEntries > 5 ? (
					<li id={`${portletNamespace}-see-more`} key="see-more">
						<ClayButton
							displayType="link"
							onClick={() =>
								toggleSidebar(
									field,
									summary,
									totalEntries,
									type
								)
							}
						>
							{Liferay.Language.get('see-all-entries')}
						</ClayButton>
					</li>
				) : null}
			</ul>
		</div>
	);
}
