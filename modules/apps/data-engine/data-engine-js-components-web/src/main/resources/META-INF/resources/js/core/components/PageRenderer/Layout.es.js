/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect} from 'react';

import {getFormId, getFormNode} from '../../../utils/formId.es';
import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useConfig} from '../../hooks/useConfig.es';
import {useEvaluate} from '../../hooks/useEvaluate.es';
import {useForm, useFormState} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import fieldBlur from '../../thunks/fieldBlur.es';
import fieldChange from '../../thunks/fieldChange.es';
import fieldFocus from '../../thunks/fieldFocus.es';
import {mergeVariants} from '../../utils/merge-variants.es';
import {Field} from '../Field/Field.es';
import {VariantsContext} from './VariantsContext.es';

export function Layout({components, editable, itemPath, rows, viewMode}) {
	const {containerElement, pageIndex} = usePage();
	const {activePage, defaultLanguageId, focusedField, pages, title} =
		useFormState();
	const {allowNestedFields, submitButtonId} = useConfig();

	const createFieldChange = useEvaluate(fieldChange);
	const dispatch = useForm();

	const variants = useContext(VariantsContext);

	const Components = components ?? mergeVariants(editable, variants);

	useEffect(() => {
		dispatch({type: EVENT_TYPES.HISTORY.RESET});
	}, [defaultLanguageId, dispatch]);

	useEffect(() => {
		const goToHandler = ({step}) => {
			dispatch({step, type: EVENT_TYPES.HISTORY.GOTO});
		};
		const handleStoreState = () => {
			dispatch({type: EVENT_TYPES.HISTORY.ADD});
		};
		const undoHandler = () => {
			dispatch({type: EVENT_TYPES.HISTORY.PREV});
		};
		const redoHandler = () => {
			dispatch({type: EVENT_TYPES.HISTORY.NEXT});
		};

		Liferay.on('journal:goto', goToHandler);
		Liferay.on('journal:undo', undoHandler);
		Liferay.on('journal:redo', redoHandler);
		Liferay.on('journal:storeState', handleStoreState);

		return () => {
			Liferay.detach('journal:goto', goToHandler);
			Liferay.detach('journal:undo', undoHandler);
			Liferay.detach('journal:redo', redoHandler);
			Liferay.detach('journal:storeState', handleStoreState);
		};
	}, [dispatch]);

	useEffect(() => {
		dispatch({type: EVENT_TYPES.HISTORY.RESET});
	}, [dispatch]);

	return (
		<Components.Rows
			activePage={activePage}
			editable={editable}
			itemPath={itemPath}
			pageIndex={pageIndex}
			rows={rows}
		>
			{({index: rowIndex, row}) => (
				<Components.Row
					itemPath={[...itemPath, rowIndex]}
					key={rowIndex}
					row={row}
				>
					{({column, index, ...otherProps}) => (
						<Components.Column
							activePage={activePage}
							allowNestedFields={allowNestedFields}
							column={column}
							editable={editable}
							index={index}
							itemPath={[...itemPath, rowIndex, index]}
							key={index}
							pageIndex={pageIndex}
							row={row}
							rowIndex={rowIndex}
							viewMode={viewMode}
							{...otherProps}
						>
							{(fieldProps) => (
								<Field
									{...fieldProps}
									activePage={activePage}
									defaultLanguageId={defaultLanguageId}
									editable={editable}
									itemPath={[
										...itemPath,
										rowIndex,
										index,
										fieldProps.index,
									]}
									key={
										fieldProps.field?.instanceId ??
										fieldProps.field.name
									}
									onBlur={(event, focusDuration) =>
										dispatch(
											fieldBlur({
												activePage,
												focusDuration,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												formPageTitle:
													pages[activePage].title,
												properties: event,
												title,
											})
										)
									}
									onChange={(properties) =>
										dispatch(
											createFieldChange({
												focusedField,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												properties,
												submitButtonId,
												viewMode,
											})
										)
									}
									onFocus={(event) =>
										dispatch(
											fieldFocus({
												activePage,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												formPageTitle:
													pages[activePage].title,
												properties: event,
												title,
											})
										)
									}
									pageIndex={pageIndex}
								/>
							)}
						</Components.Column>
					)}
				</Components.Row>
			)}
		</Components.Rows>
	);
}
