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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

const LanguageListItem = ({
	displayName,
	isDefault,
	localeId,
	onMakeDefault,
	showActions
}) => {
	const [active, setActive] = useState(false);
	const makeDefault = event => {
		setActive(false);
		onMakeDefault({localeId});
		Liferay.fire('inputLocalized:defaultLocaleChanged', {
			item: event.currentTarget
		});
	};

	return (
		<ClayTable.Row>
			<ClayTable.Cell expanded>
				{displayName}
				{isDefault && (
					<ClayLabel className="ml-3" displayType="info">
						{Liferay.Language.get('default')}
					</ClayLabel>
				)}
			</ClayTable.Cell>
			{showActions && (
				<ClayTable.Cell align="right">
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton displayType="unstyled" monospaced small>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								data-value={localeId}
								key={localeId}
								onClick={event => makeDefault(event)}
							>
								{Liferay.Language.get('make-default')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayTable.Cell>
			)}
		</ClayTable.Row>
	);
};

export default LanguageListItem;
