## JSPArrowFunctionCheck

Arrow functions were introduced with ES6. IE11 does not support ES6.

Use a regular function instead.

### Example

Incorrect:

```
Liferay.componentReady('editRoleAssignmentsManagementToolbar').then(
    (managementToolbar) => {
        managementToolbar.on('creationButtonClicked', addAssignees);
    }
);
```

Correct:

```
Liferay.componentReady('editRoleAssignmentsManagementToolbar').then(
    function(managementToolbar) {
        managementToolbar.on('creationButtonClicked', addAssignees);
    }
);
```