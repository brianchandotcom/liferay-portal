## MVCCommandNameCheck

Naming rules for values of `mvc.command.name`

- The value should start with `/` and consist of a **path name** and an
**action name**: `/path_name/action_name`

---

- The **path name** should match one of the following criteria:

  - Follow the name of the module

    - Class is located in `portal-reports-engine-console-web`:
    `mvc.command.name=/portal_reports_engine_console/action_name`

  - Follow the portlet name (value of `javax.portlet.name`)

    #### Example:

    ```
    @Component(
        immediate = true,
        property = {
            "javax.portlet.name=" + ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
            "mvc.command.name=/reports_admin/action_name"
        },
        service = MVCActionCommand.class
    )
    ```

  - Follow the component name of a referenced variable

    #### Example:

    ```
    @Component(
        immediate = true,
        property = {
            "javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
            "mvc.command.name=/users_admin/action_name"
        },
        service = MVCActionCommand.class
    )
    ...

    @Reference(
        target = "(component.name=com.liferay.users.admin.web.internal.portlet.action.UpdatePasswordMVCActionCommand)"
    )
    private MVCActionCommand _mvcActionCommand;
    ```

---

- When there is only one `mvc.command.name`, the **action name** should match
one of the following criteria:

  - Follow the name of the class

    #### Example:

    ```
    @Component(
        immediate = true,
        property = {
            "javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
            "mvc.command.name=/kaleo_designer/duplicate_workflow_definition"
        },
        service = MVCActionCommand.class
    )
    public class DuplicateWorkflowDefinitionMVCActionCommand
        extends PublishKaleoDefinitionVersionMVCActionCommand {
    ```

  - Path name and action name together follow the name of the class

    #### Example:

    ```
    @Component(
        immediate = true,
        property = {
            "javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
            "mvc.command.name=/", "mvc.command.name=/wiki_admin/view"
        },
        service = MVCRenderCommand.class
    )
    public class WikiAdminViewMVCRenderCommand
        extends BaseViewPageMVCRenderCommand {
    ```

  - Follow the component name of a referenced variable

    #### Example:

    ```
    @Component(
        immediate = true,
        property = {
            "javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
            "mvc.command.name=/users_admin/update_password"
        },
        service = MVCActionCommand.class
    )
    public class UpdatePasswordMyAccountMVCActionCommand
        extends BaseMVCActionCommand {
    ...

    @Reference(
        target = "(component.name=com.liferay.users.admin.web.internal.portlet.action.UpdatePasswordMVCActionCommand)"
    )
    private MVCActionCommand _mvcActionCommand;
    ```