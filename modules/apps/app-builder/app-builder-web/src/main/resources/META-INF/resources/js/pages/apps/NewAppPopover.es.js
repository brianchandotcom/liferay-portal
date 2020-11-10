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
import {compile} from 'path-to-regexp';
import React, {useContext, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Popover from '../../components/popover/Popover.es';
import SelectObjects from '../../components/select-objects/SelectObjects.es';
import useBackUrl from '../../hooks/useBackUrl.es';

const NewAppPopover = (
	{alignElement, editPath, history, onCancel, visible},
	forwardRef
) => {
	const {objectsPortletURL} = useContext(AppContext);
	const [selectedObject, setSelectedObject] = useState({});
	const withBackUrl = useBackUrl();

	const onClick = () => {
		history.push(
			withBackUrl(
				compile(editPath[0])({dataDefinitionId: selectedObject.id})
			)
		);
	};

	const onCreateObject = (newObject) => {
		Liferay.Util.navigate(
			Liferay.Util.PortletURL.createRenderURL(objectsPortletURL, {
				dataDefinitionId: newObject.id,
				mvcRenderCommandName: '/app_builder/edit_form_view',
				newCustomObject: true,
			})
		);
	};

	return (
		<>
			<Popover
				alignElement={alignElement}
				className="apps-popover"
				content={() => (
					<div className="px-2">
						<label>{Liferay.Language.get('object')}</label>

						<SelectObjects
							alignElement={alignElement}
							label={Liferay.Language.get('select-object')}
							onCreateObject={onCreateObject}
							onSelect={setSelectedObject}
							selectedValue={selectedObject}
							visible={visible}
						/>
					</div>
				)}
				footer={() => (
					<div
						className="border-top mt-3 px-4 py-3"
						style={{width: 450}}
					>
						<div className="d-flex justify-content-end">
							<ClayButton
								className="mr-3"
								displayType="secondary"
								onClick={() => {
									setSelectedObject({});
									onCancel();
								}}
								small
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!selectedObject.id}
								onClick={onClick}
								small
							>
								{Liferay.Language.get('continue')}
							</ClayButton>
						</div>
					</div>
				)}
				ref={forwardRef}
				showArrow={false}
				title={() => (
					<div className="pt-2 px-2">
						<h4 className="mb-3">
							{Liferay.Language.get('new-app')}
						</h4>

						<span className="font-weight-light text-secondary">
							{Liferay.Language.get(
								'create-an-app-to-collect-and-manage-an-objects-data'
							)}
						</span>
					</div>
				)}
				visible={visible}
			/>
		</>
	);
};

export default React.forwardRef(NewAppPopover);
