/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useEffect} from 'react';

import RequiredMark from '../../../../../components/RequiredMark';
import ValidationFeedback from '../../../../../components/ValidationFeedback';
import getAllPicklists from '../../../../../utils/getAllPicklists';
import {IPickList} from '../../../../../utils/types';

function ObjectPicklist({
	namespace,
	onChange,
	onPicklistsLoad,
	picklists,
	selectedPicklist,
	sourceValidationError,
}: {
	namespace: string;
	onChange: Function;
	onPicklistsLoad: (picklists: Array<IPickList>) => void;
	picklists?: Array<IPickList>;
	selectedPicklist?: IPickList;
	sourceValidationError: boolean;
}) {
	const objectPicklistFormElementId = `${namespace}ObjectPicklist`;

	useEffect(() => {
		getAllPicklists().then((picklists) => {
			onPicklistsLoad(picklists);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!picklists) {
		return (
			<div className="position-relative">
				<ClayLoadingIndicator />
			</div>
		);
	}

	return (
		<>
			{!picklists.length ? (
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
							id={objectPicklistFormElementId}
							name={objectPicklistFormElementId}
							onChange={(event) => {
								const picklist = picklists?.find(
									(item) =>
										String(item.externalReferenceCode) ===
										event.target.value
								);

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
