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
    build.auto.upgrade=true<#if !osgiModule>

##
## Spring
##

    #
    # Input a list of comma delimited Spring configurations. These will be
    # loaded after the bean definitions specified in the
    # portalContextConfigLocation parameter in web.xml.
    #
    spring.configs=\
        WEB-INF/classes/META-INF/portlet-spring.xml,\
        \
        WEB-INF/classes/META-INF/ext-spring.xml</#if>