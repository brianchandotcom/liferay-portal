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

import React, {createContext, useState} from 'react';

const ModalContext = createContext();

const ModalProvider = ({children, processId}) => {
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

	const modalState = {
		bulkReassign,
		bulkTransition,
		processId,
		selectTasks,
		setBulkReassign,
		setBulkTransition,
		setSelectTasks,
		setSingleTransition,
		setUpdateDueDate,
		setVisibleModal,
		singleTransition,
		updateDueDate,
		visibleModal,
	};

	return (
		<ModalContext.Provider value={modalState}>
			{children}
		</ModalContext.Provider>
	);
};

export {ModalContext};
export default ModalProvider;
