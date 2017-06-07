## **Module Directory Structure**

When an application has multiple modules, the directory names for the modules
should always match the directory name of the application.

### **Example**

The directories of the modules inside the application ```
\apps\collaboration\bookmarks``` should all start with
```bookmarks-```:

* bookmarks-api
* bookmarks-service
* bookmarks-test
* bookmarks-web

---

A module directory name should not end with ```-taglib-web```. Instead,
it should end with ```-taglib```.