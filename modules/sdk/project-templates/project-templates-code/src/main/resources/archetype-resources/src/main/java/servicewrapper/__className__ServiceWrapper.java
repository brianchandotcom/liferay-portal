/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ${package}.${template};

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceWrapper;
import ${serviceWrapperClassPackage}.${serviceWrapperClassName};

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(service = ServiceWrapper.class)
public class ${className}ServiceWrapper extends ${serviceWrapperClassName} {

	public ${className}ServiceWrapper() {
		super(null);
	}
}