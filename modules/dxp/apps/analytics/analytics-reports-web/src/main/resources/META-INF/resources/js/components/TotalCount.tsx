/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useStateSafe} from '@liferay/frontend-js-react-web';
import React, {ReactNode, useContext, useEffect} from 'react';

import ConnectionContext from '../context/ConnectionContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import {numberFormat} from '../utils/numberFormat';
import {Hint, Position} from './Hint';

interface Props {
	className: string;
	dataProvider: () => Promise<string>;
	label: string;
	percentage?: boolean;
	popoverHeader: string;
	popoverMessage: string;
	popoverPosition?: Position;
}

function TotalCount({
	className,
	dataProvider,
	label,
	percentage = false,
	popoverHeader,
	popoverMessage,
	popoverPosition,
}: Props) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [value, setValue] = useStateSafe('-');

	const dispatch = useContext(StoreDispatchContext);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	useEffect(() => {
		if (validAnalyticsConnection) {
			dataProvider()
				.then(setValue)
				.catch(() => {
					setValue('-');
					dispatch({type: 'ADD_WARNING'});
				});
		}
	}, [dispatch, dataProvider, setValue, validAnalyticsConnection]);

	let displayValue: ReactNode = '-';

	if (validAnalyticsConnection && languageTag && !publishedToday) {
		displayValue =
			value !== '-' ? (
				percentage ? (
					<span>{`${value}%`}</span>
				) : (
					numberFormat(languageTag, Number(value))
				)
			) : (
				value
			);
	}

	return (
		<div className={className}>
			<span className="text-secondary">{label}</span>

			<span className="text-secondary">
				<Hint
					message={popoverMessage}
					position={popoverPosition}
					title={popoverHeader}
				/>
			</span>

			<span className="font-weight-bold inline-item-after">
				{displayValue}
			</span>
		</div>
	);
}

export default TotalCount;
