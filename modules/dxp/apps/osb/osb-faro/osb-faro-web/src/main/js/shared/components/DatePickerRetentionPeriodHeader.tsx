import React from 'react';
import {Text as ClayText} from '@clayui/core';
import {sub} from 'shared/util/lang';

export const DatePickerRetentionPeriodHeader = ({retentionPeriod}) => (
	<>
		<ClayText size={2} weight='semi-bold'>
			{Liferay.Language.get('custom-range').toUpperCase()}
		</ClayText>

		<div>
			<ClayText color='secondary' size={2}>
				{sub(
					Liferay.Language.get(
						'dates-prior-to-x-months-cannot-be-selected-due-to-your-workspaces-data-retention-period'
					),
					[retentionPeriod]
				)}
			</ClayText>
		</div>
	</>
);
