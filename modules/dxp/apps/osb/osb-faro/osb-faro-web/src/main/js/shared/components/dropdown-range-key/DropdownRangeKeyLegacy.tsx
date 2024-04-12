import ClayDropDown from '@clayui/drop-down';
import getCN from 'classnames';
import React from 'react';
import {Text as ClayText} from '@clayui/core';

export const DropdownRangeKeyLegacy = ({
	onClickSeeMore,
	onClickShowDatePicker,
	seeMore,
	selectedItem
}) => (
	<>
		{!seeMore && (
			<ClayDropDown.Item
				className='c-pointer'
				key='SEE_MORE'
				onClick={onClickSeeMore}
			>
				{Liferay.Language.get('more-preset-periods')}
			</ClayDropDown.Item>
		)}

		<ClayDropDown.Divider />

		<ClayDropDown.Item
			className={getCN('c-pointer', {
				active: selectedItem?.value === 'CUSTOM'
			})}
			key='CUSTOM'
			onClick={onClickShowDatePicker}
		>
			<ClayText size={3} weight='semi-bold'>
				{Liferay.Language.get('custom-range')}
			</ClayText>
		</ClayDropDown.Item>
	</>
);
