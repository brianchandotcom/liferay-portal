/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useState} from 'react';

const ModalContext = createContext();

export default function ModalProvider({children, processId}) {
	const [bulkReassign, setBulkReassign] = useState({
		reassignedTasks: [],
		reassigning: false,
		selectedAssignee: null,
		useSameAssignee: false,
	});
	const [bulkTransition, setBulkTransition] = useState({
		transition: {errors: {}, onGoing: false},
		transitionTasks: [],
	});
	const [fetchOnClose, setFetchOnClose] = useState(false);
	const [selectTasks, setSelectTasks] = useState({
		selectAll: false,
		tasks: [],
	});
	const [singleTransition, setSingleTransition] = useState({
		title: '',
		transitionName: '',
	});
	const [updateDueDate, setUpdateDueDate] = useState({
		comment: undefined,
		dueDate: undefined,
	});
	const [visibleModal, setVisibleModal] = useState('');

	const closeModal = (refetch) => {
		setFetchOnClose(refetch);
		setVisibleModal('');
	};

	const modalState = {
		bulkReassign,
		bulkTransition,
		closeModal,
		fetchOnClose,
		openModal: setVisibleModal,
		processId,
		selectTasks,
		setBulkReassign,
		setBulkTransition,
		setSelectTasks,
		setSingleTransition,
		setUpdateDueDate,
		singleTransition,
		updateDueDate,
		visibleModal,
	};

	return (
		<ModalContext.Provider value={modalState}>
			{children}
		</ModalContext.Provider>
	);
}

export {ModalContext};
