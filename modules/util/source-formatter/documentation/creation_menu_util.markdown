## CreationMenuUtil

Use `CreationMenuUtil`, when possible.

### Example

Use

```java
public CreationMenu getCreationMenu() {
    return CreationMenuUtil.addPrimaryDropdownItem(
        dropdownItem -> {
            dropdownItem.setLabel(LanguageUtil.get(request, "add-user"));
        }
    ).addDropdownItem(
        dropdownItem -> {
            dropdownItem.putData("action", "selectAccountUsers");

            long accountEntryId = ParamUtil.getLong(
                request, "accountEntryId");

            AccountEntry accountEntry =
                AccountEntryLocalServiceUtil.fetchAccountEntry(
                    accountEntryId);

            if (accountEntry != null) {
                dropdownItem.putData(
                    "accountEntryName", accountEntry.getName());
            }

            dropdownItem.setLabel(
                LanguageUtil.get(request, "assign-users"));
        }
    );
}
```

Instead of

```java
public CreationMenu getCreationMenu() {
    return new CreationMenu() {
        {
            addPrimaryDropdownItem(
                dropdownItem -> {
                    dropdownItem.setLabel(
                        LanguageUtil.get(request, "add-user"));
                });

            addDropdownItem(
                dropdownItem -> {
                    dropdownItem.putData("action", "selectAccountUsers");

                    long accountEntryId = ParamUtil.getLong(
                        request, "accountEntryId");

                    AccountEntry accountEntry =
                        AccountEntryLocalServiceUtil.fetchAccountEntry(
                            accountEntryId);

                    if (accountEntry != null) {
                        dropdownItem.putData(
                            "accountEntryName", accountEntry.getName());
                    }

                    dropdownItem.setLabel(
                        LanguageUtil.get(request, "assign-users"));
                });
        }
    };
}
```
or

```java
public CreationMenu getCreationMenu() {
    CreationMenu creationMenu = new CreationMenu();

    creationMenu.addPrimaryDropdownItem(
        dropdownItem -> {
            dropdownItem.setLabel(LanguageUtil.get(request, "add-user"));
        });

    creationMenu.addDropdownItem(
        dropdownItem -> {
            dropdownItem.putData("action", "selectAccountUsers");

            long accountEntryId = ParamUtil.getLong(
                request, "accountEntryId");

            AccountEntry accountEntry =
                AccountEntryLocalServiceUtil.fetchAccountEntry(
                    accountEntryId);

            if (accountEntry != null) {
                dropdownItem.putData(
                    "accountEntryName", accountEntry.getName());
            }

            dropdownItem.setLabel(
                LanguageUtil.get(request, "assign-users"));
        });

    return creationMenu;
}
```