/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, { useCallback } from "react";

import { getWorkflowTasksAssignedToMe, getWorkflowTasksAssignedToMyRoles } from "../../../common/services/WorkflowService";
import { WorkflowTask } from "../../../common/types/WorkflowTask";
import { WorkflowTaskResponse } from "../../../common/types/WorkflowTaskResponse";

interface FetchWorkflowTasksInterface {
    delta: number,
    page: number,
    selectedItem: {label: string, value: string},
    setTotalCount: React.Dispatch<React.SetStateAction<number>>
    setWorkflowTasks: React.Dispatch<React.SetStateAction<WorkflowTaskResponse[]>>,
    workflowProps: any,

}
export default function useFetchWorkflowTasks({delta, page, selectedItem, setTotalCount, setWorkflowTasks, workflowProps}: FetchWorkflowTasksInterface) {
    const fetchWorkflowTasks = useCallback(async () => {
        try {
            if (selectedItem.value === 'assigned-to-me') {
    
                const res = await getWorkflowTasksAssignedToMe({
                    page,
                    pageSize: delta,
                });

                const filteredWorkflowTasks = res.items.map((task) => {
                    const matchingWorkflowTask = workflowProps?.currentWorkflowTasks?.find((propTask: WorkflowTask) => String(propTask?.workflowId) === String(task.id));

                        return {
                            ...task,
                            auditUser: matchingWorkflowTask?.auditUser || 'Someone'
                        }

                })
    
                setWorkflowTasks(filteredWorkflowTasks);
                setTotalCount(filteredWorkflowTasks.length);
            }
            else {
    
                const res = await getWorkflowTasksAssignedToMyRoles({
                    page,
                    pageSize: delta,
                });
    
                const filteredWorkflowTasks = res.items.map((task) => {
                    const matchingWorkflowTask = workflowProps?.workflowTasksByCurrentUserRoles?.find((propTask: WorkflowTask) => String(propTask?.workflowId) === String(task.id));
    
                    
                        return {
                            ...task,
                            auditUser: matchingWorkflowTask?.auditUser || 'Someone'
                        }
                })

    
                setWorkflowTasks(filteredWorkflowTasks);
                setTotalCount(filteredWorkflowTasks.length);
            }
        }
        catch (error) {
            console.error(error);
            setWorkflowTasks([]);
        }
    }, [delta, page, selectedItem.value, setTotalCount, setWorkflowTasks, workflowProps?.currentWorkflowTasks, workflowProps?.workflowTasksByCurrentUserRoles]);

    return {fetchWorkflowTasks};
}

