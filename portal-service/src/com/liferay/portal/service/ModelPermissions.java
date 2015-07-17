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

package com.liferay.portal.service;

import com.liferay.portal.model.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissions implements Serializable {

    public void addRolePermissions(Role role, String[] actionIds) {
        for (String actionId : actionIds) {
            addRolePermissions(role, actionId);
        }
    }
    
    public void addRolePermissions(Role role, String actionId) {
        getRoles(actionId).add(role);
        getActionIds(role).add(actionId);

        _roles.add(role);
    }

    public List<String> getActionIds(Role role) {
        List<String> actionIds = _rolesMap.get(role.getName());

        if (actionIds == null) {
            actionIds = new ArrayList<>();

            _rolesMap.put(role.getName(), actionIds);
        }

        return actionIds;
    }

    public List<Role> getRoles() {
        return _roles;
    }

    public List<Role> getRoles(String actionId) {
        List<Role> roles = _actionsMap.get(actionId);

        if (roles == null) {
            roles = new ArrayList<>();

            _actionsMap.put(actionId, roles);
        }

        return roles;
    }

    public boolean isEmpty() {
        return _roles.isEmpty();
    }

    private Map<String, List<Role>> _actionsMap = new HashMap<>();
    private Map<String, List<String>> _rolesMap = new HashMap<>();
    private List<Role> _roles = new ArrayList<>();

}
