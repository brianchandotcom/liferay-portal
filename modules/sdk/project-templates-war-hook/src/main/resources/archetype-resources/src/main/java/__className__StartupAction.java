package ${package};

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;

import java.util.stream.Stream;

/**
 * @author ${author}
 */
public class ${className}StartupAction extends SimpleAction {

	@Override
	public void run(String[] lifecycleEventIds) throws ActionException {
		Stream<String> lifecycleEventIdsStream = Stream.of(lifecycleEventIds);

		lifecycleEventIdsStream.map(
			id -> "startup event id " + id
		).forEach(
			System.out::println
		);

	}

}