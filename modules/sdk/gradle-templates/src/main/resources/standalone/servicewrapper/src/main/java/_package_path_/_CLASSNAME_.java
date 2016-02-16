package _package_;

import _SERVICE_FULL_;

import com.liferay.portal.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class _CLASSNAME_ extends _SERVICE_SHORT_ {

        public _CLASSNAME_() {
            super(null);
        }

}
