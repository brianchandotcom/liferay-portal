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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {useDebounceCallback} from '../utils/utils.es';

export default withRouter(
	({
		 history,
		 match: {
			 params: {sectionId},
		 },
		 filterChange,
		 searchChange,
	 }) => {
		const context = useContext(AppContext);

		const [debounceCallback] = useDebounceCallback(value => {
			searchChange(value);
		}, 500);

		const item = value => ({value, label: Liferay.Language.get(value)});

		const options = [
			item('latest-created'),
			item('latest-edited'),
			item('week'),
			item('month')
		];

		return (
			<div
				className="autofit-padded-no-gutters autofit-row autofit-row-center">

				<div className="d-flex">
					{Liferay.Language.get('filter-by')}

					<ClaySelect
						onChange={filterChange}
					>
						{options.map(item => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</div>

				<div className="d-flex">
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								className="form-control input-group-inset input-group-inset-after"
								onChange={event =>
									debounceCallback(event.target.value)
								}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					{context.canCreateThread && (
						<>
							<ClayButton
								className="c-ml-3 d-none d-sm-block text-nowrap"
								displayType="primary"
								onClick={() =>
									history.push(
										'/questions/' + sectionId + '/new'
									)
								}
							>
								{Liferay.Language.get('ask-question')}
							</ClayButton>

							<ClayButton
								className="btn-monospaced d-block d-sm-none position-fixed question-button shadow"
								displayType="primary"
								onClick={() =>
									history.push(
										'/questions/' + sectionId + '/new'
									)
								}
							>
								<ClayIcon symbol="pencil"/>

								<span className="sr-only">
									{Liferay.Language.get('ask-question')}
								</span>
							</ClayButton>
						</>
					)}
				</div>
			</div>
		);
	}
);
