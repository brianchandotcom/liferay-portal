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

import {Context as ModalContext} from '@clayui/modal';
import {DragLayer} from 'data-engine-taglib';
import {Pages, useForm, useFormState} from 'dynamic-data-mapping-form-renderer';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'dynamic-data-mapping-form-renderer/js/core/actions/eventTypes.es';
import React, {useContext} from 'react';

import fieldDelete from './thunks/fieldDelete';

const FormBuilder = () => {
	const dispatch = useForm();
	const [{onClose}, modalDispatch] = useContext(ModalContext);

	const {rules} = useFormState();

	return (
		<div className="data-engine-form-builder ddm-form-builder pb-5">
			<div className="sheet">
				<div className="ddm-form-builder-wrapper">
					<div className="container ddm-form-builder">
						<DragLayer />
						<Pages
							editable={true}
							fieldActions={[
								{
									action: (payload) =>
										dispatch({
											payload,
											type:
												CORE_EVENT_TYPES.FIELD
													.DUPLICATE,
										}),
									label: Liferay.Language.get('duplicate'),
								},
								{
									action: (payload) =>
										dispatch(
											fieldDelete({
												action: {
													payload,
													type:
														CORE_EVENT_TYPES.FIELD
															.DELETE,
												},
												modalDispatch,
												onClose,
												rules,
											})
										),
									label: Liferay.Language.get('delete'),
								},
							]}
						/>
					</div>
				</div>
			</div>
		</div>
	);
};

export default FormBuilder;
