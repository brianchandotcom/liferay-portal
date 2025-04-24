## AnonymousClassCheck

Do not use an anonymous class when passing the variable to method
`addBackgroundTask`. This will cause problems because it creates a
non-serializable class.

### Example

Incorrect:

```
private BackgroundTask _addBackgroundTask(
        String cmd, FileEntry fileEntry, long userId)
    throws PortalException {

    Map<String, Serializable> taskContextMap =
        new HashMap<String, Serializable>() {
            {
                put(GoogleDriveBackgroundTaskConstants.CMD, cmd);
                put(
                    GoogleDriveBackgroundTaskConstants.COMPANY_ID,
                    fileEntry.getCompanyId());
                put(
                    BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS,
                    true);
                put(
                    GoogleDriveBackgroundTaskConstants.FILE_ENTRY_ID,
                    fileEntry.getFileEntryId());
                put(GoogleDriveBackgroundTaskConstants.USER_ID, userId);
            }
        };

    return _backgroundTaskManager.addBackgroundTask(
        userId, CompanyConstants.SYSTEM,
        StringBundler.concat(
            DLOpenerGoogleDriveManager.class.getSimpleName(),
            StringPool.POUND, fileEntry.getFileEntryId()),
        UploadGoogleDriveDocumentBackgroundTaskExecutor.class.getName(),
        taskContextMap, new ServiceContext());
}
```

Correct:

```
private BackgroundTask _addBackgroundTask(
        String cmd, FileEntry fileEntry, long userId)
    throws PortalException {

    Map<String, Serializable> taskContextMap =
        HashMapBuilder.<String, Serializable>put(
            BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true
        ).put(
            GoogleDriveBackgroundTaskConstants.CMD, cmd
        ).put(
            GoogleDriveBackgroundTaskConstants.COMPANY_ID,
            fileEntry.getCompanyId()
        ).put(
            GoogleDriveBackgroundTaskConstants.FILE_ENTRY_ID,
            fileEntry.getFileEntryId()
        ).put(
            GoogleDriveBackgroundTaskConstants.USER_ID, userId
        ).build();

    return _backgroundTaskManager.addBackgroundTask(
        userId, CompanyConstants.SYSTEM,
        StringBundler.concat(
            DLOpenerGoogleDriveManager.class.getSimpleName(),
            StringPool.POUND, fileEntry.getFileEntryId()),
        UploadGoogleDriveDocumentBackgroundTaskExecutor.class.getName(),
        taskContextMap, new ServiceContext());
}
```