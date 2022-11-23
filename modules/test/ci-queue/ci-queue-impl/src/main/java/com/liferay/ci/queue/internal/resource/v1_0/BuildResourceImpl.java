package com.liferay.ci.queue.internal.resource.v1_0;

import com.liferay.ci.queue.resource.v1_0.BuildResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Michael Hashimoto
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/build.properties",
	scope = ServiceScope.PROTOTYPE, service = BuildResource.class
)
public class BuildResourceImpl extends BaseBuildResourceImpl {
}