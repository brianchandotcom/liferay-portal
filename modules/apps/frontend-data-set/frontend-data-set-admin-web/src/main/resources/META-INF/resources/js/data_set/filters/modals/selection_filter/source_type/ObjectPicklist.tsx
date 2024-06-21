/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import RequiredMark from '../../../../../components/RequiredMark';
import ValidationFeedback from '../../../../../components/ValidationFeedback';
import getAllPicklists from '../../../../../utils/getAllPicklists';
import {IFilter, IPickList} from '../../../../../utils/types';

interface IObjectPicklistProps {
	filter?: IFilter;
	namespace: string;
	onChange: Function;
	sourceValidationError: boolean;
}

function ObjectPicklist({
	filter,
	namespace,
	onChange,
	sourceValidationError,
}: IObjectPicklistProps) {
	const [picklists, setPicklists] = useState<IPickList[]>();
	const [selectedPicklist, setSelectedPicklist] = useState<IPickList>();

	const objectPicklistFormElementId = `${namespace}ObjectPicklist`;

	useEffect(() => {
		getAllPicklists().then((items) => {
			setPicklists(items);
		});
	}, []);

	useEffect(() => {
		const picklist = picklists?.find((item) =>
			Liferay.FeatureFlags['LPD-10754']
				? String(item.externalReferenceCode) === (filter as any)?.source
				: String(item.externalReferenceCode) ===
					(filter as any)?.listTypeDefinitionERC
		);

		if (picklist) {
			setSelectedPicklist(picklist);
		}
	}, [filter, picklists]);

	return (
		<>
			{picklists && !picklists.length ? (
				<ClayAlert displayType="info" title="Info">
					{Liferay.Language.get(
						'no-filter-sources-are-available.-create-a-picklist-or-a-vocabulary-for-this-type-of-filter'
					)}
				</ClayAlert>
			) : (
				<ClayForm.Group
					className={classNames({
						'has-error': sourceValidationError,
					})}
				>
					<label htmlFor={objectPicklistFormElementId}>
						{Liferay.Language.get('picklist')}

						<RequiredMark />
					</label>

					{picklists && (
						<ClaySelectWithOption
							aria-label={Liferay.Language.get('picklist')}
							name={objectPicklistFormElementId}
							onChange={(event) => {
								const picklist = picklists?.find(
									(item) =>
										String(item.externalReferenceCode) ===
										event.target.value
								);
								setSelectedPicklist(picklist);
								onChange(picklist);
							}}
							options={[
								{
									disabled: true,
									label: Liferay.Language.get(
										'choose-an-option'
									),
									value: '',
								},
								...picklists.map((item) => ({
									label: item.name,
									value: item.externalReferenceCode,
								})),
							]}
							required
							title={Liferay.Language.get('source-options')}
							value={
								selectedPicklist?.externalReferenceCode || ''
							}
						/>
					)}

					{sourceValidationError && <ValidationFeedback />}
				</ClayForm.Group>
			)}
		</>
	);
}

export default ObjectPicklist;
