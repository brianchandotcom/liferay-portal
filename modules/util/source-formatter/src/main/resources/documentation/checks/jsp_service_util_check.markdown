## JSPServiceUtilCheck

Do not make calls to methods in `*ServiceUtil` from module jsp.

Instead, globally used service calls should be moved to the controller and
results should be passed as a request attribute. In case, there are many usages
or the service calls are conditional, a service holder can be created, that can
be passed as a request attribute.