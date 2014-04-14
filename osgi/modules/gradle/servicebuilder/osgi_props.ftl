##
## Properties Override
##

    #
    # Specify where to get the overridden properties. Updates should not be made
    # on this file but on the overridden version of this file.
    #
    include-and-override=${r"${base.path}"}/service-ext.properties

##
## Build
##

    build.namespace=${portletShortName}
    build.number=${buildNumber?c}
    build.date=${currentTimeMillis?c}
    build.auto.upgrade=true
