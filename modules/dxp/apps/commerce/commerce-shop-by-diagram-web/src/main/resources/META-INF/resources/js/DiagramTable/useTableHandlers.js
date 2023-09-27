/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
