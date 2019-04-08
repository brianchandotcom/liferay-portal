package com.liferay.talend.source;

import static java.util.Arrays.asList;

import static org.junit.Assert.assertEquals;

import com.liferay.talend.configuration.LiferayInputMapperConfiguration;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.runtime.input.Mapper;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayInputMapperTest {

	@ClassRule
	public static final SimpleComponentRule COMPONENT_FACTORY =
		new SimpleComponentRule("com.liferay.talend");

	@Ignore("You need to complete this test")
	@Test
	public void produce() throws IOException {

        // Source configuration
        // Setup your component configuration for the test here
        final LiferayInputMapperConfiguration configuration =  new LiferayInputMapperConfiguration()
                                                                            /* .setRestDataSet()
                                                                               .setTimeout()
                                                                               .setMaxItemsPerRequest() */;

		// We create the component mapper instance using the configuration filled above

		final Mapper mapper = COMPONENT_FACTORY.createMapper(
			LiferayInputMapper.class, configuration);

        // Collect the source as a list
        assertEquals(asList(/* TODO - give the expected data */), COMPONENT_FACTORY.collectAsList(Record.class, mapper));
	}

}