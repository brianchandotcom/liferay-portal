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

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ErrorBoundary from '../shared/ErrorBoundary.es';
import AddResultModal from './AddResultModal.es';

/**
 * A button that opens a modal to be able to search, select, and add results.
 */
function AddResult({fetchDocumentsSearchUrl, onAddResultSubmit}) {
	const [showModal, setShowModal] = useState(false);

	/**
	 * Opens the modal when the add result button is clicked.
	 */
	function _handleAddResultButton() {
		setShowModal(true);
	}

	/**
	 * Hides the modal.
	 */
	function _handleCloseModal() {
		setShowModal(false);
	}

	return (
		<>
			<ClayButton
				key="ADD_RESULT_BUTTON"
				onClick={_handleAddResultButton}
			>
				{Liferay.Language.get('add-result')}
			</ClayButton>

			<ErrorBoundary component={Liferay.Language.get('add-result')} toast>
				{showModal ? (
					<AddResultModal
						fetchDocumentsSearchUrl={fetchDocumentsSearchUrl}
						onAddResultSubmit={onAddResultSubmit}
						onCloseModal={_handleCloseModal}
					/>
				) : null}
			</ErrorBoundary>
		</>
	);
}

AddResult.propTypes = {
	fetchDocumentsSearchUrl: PropTypes.string.isRequired,
	onAddResultSubmit: PropTypes.func.isRequired
};

export default AddResult;
