package _package_;

import _SERVICE_FULL_;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class _CLASS_NAME_ extends _SERVICE_SHORT_ {

	public _CLASS_NAME_() {
		super(null);
	}

}
