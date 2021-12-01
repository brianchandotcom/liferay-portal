/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useEffect} from 'react';

import {DIAGRAM_TABLE_EVENTS} from '../utilities/constants';

function useTableHandlers(chartInstance, productId, updateDiagram) {
	useEffect(() => {
		const handlePinHighlightByTable = ({diagramProductId, sequence}) => {
			if (diagramProductId === productId) {
				chartInstance.current.highlight(sequence);
			}
		};

		const handleRemovePinHighlightByTable = ({
			diagramProductId,
			sequence,
		}) => {
			if (diagramProductId === productId) {
				chartInstance.current.removeHighlight(sequence);
			}
		};

		const handleSelectPinByTable = ({diagramProductId, product}) => {
			if (diagramProductId === productId) {
				chartInstance.current.selectPinByProduct(product);
			}
		};

		const handlePinsUpdatedByTable = ({diagramProductId}) => {
			if (diagramProductId === productId) {
				updateDiagram();
			}
		};

		Liferay.on(DIAGRAM_TABLE_EVENTS.SELECT_PIN, handleSelectPinByTable);
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.HIGHLIGHT_PIN,
			handlePinHighlightByTable
		);
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.REMOVE_PIN_HIGHLIGHT,
			handleRemovePinHighlightByTable
		);
		Liferay.on(
			DIAGRAM_TABLE_EVENTS.TABLE_UPDATED,
			handlePinsUpdatedByTable
		);

		return () => {
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.SELECT_PIN,
				handleSelectPinByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.HIGHLIGHT_PIN,
				handlePinHighlightByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.REMOVE_PIN_HIGHLIGHT,
				handleRemovePinHighlightByTable
			);
			Liferay.detach(
				DIAGRAM_TABLE_EVENTS.TABLE_UPDATED,
				handlePinsUpdatedByTable
			);
		};
	}, [chartInstance, productId, updateDiagram]);

	return null;
}

export default useTableHandlers;
