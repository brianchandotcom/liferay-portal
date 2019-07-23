/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.scr.impl.config;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.felix.scr.impl.manager.ScrConfiguration;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * The <code>ScrManagedServiceMetaTypeProvider</code> receives the Declarative
 * Services Runtime configuration (by extending the {@link ScrManagedService}
 * class.
 * <p>
 * This class is instantiated in a ServiceFactory manner by the
 * {@link ScrManagedServiceServiceFactory} when the Configuration Admin service
 * implementation and API is available
 * <p>
 * Requires OSGi Metatype Service API available
 *
 * @see ScrManagedServiceServiceFactory
 */
class ScrMetaTypeProvider implements MetaTypeProvider
{

    private final ScrConfiguration configuration;

    public ScrMetaTypeProvider(final ScrConfiguration scrConfiguration)
    {
        this.configuration = scrConfiguration;
    }

    /**
     * @see org.osgi.service.metatype.MetaTypeProvider#getLocales()
     */
    @Override
    public String[] getLocales()
    {
        return null;
    }

    /**
     * @see org.osgi.service.metatype.MetaTypeProvider#getObjectClassDefinition(java.lang.String, java.lang.String)
     */
    @Override
    public ObjectClassDefinition getObjectClassDefinition( String id, String locale )
    {
        if ( !ScrConfiguration.PID.equals( id ) )
        {
            return null;
        }

        final ArrayList<AttributeDefinition> adList = new ArrayList<>();

        adList.add(new AttributeDefinitionImpl(ScrConfiguration.PROP_LOGLEVEL, "ds-loglevel",
            "ds-loglevel-help", AttributeDefinition.INTEGER,
            new String[]
                { String.valueOf(this.configuration.getLogLevel()) }, 0, new String[]
                { "Debug", "Information", "Warnings", "Error" }, new String[]
                { "4", "3", "2", "1" }));

        adList
        .add(new AttributeDefinitionImpl(
            ScrConfiguration.PROP_FACTORY_ENABLED,
            "ds-factory-enabled", "ds-factory-enabled-help", this.configuration.isFactoryEnabled()));

        adList.add( new AttributeDefinitionImpl(
                ScrConfiguration.PROP_DELAYED_KEEP_INSTANCES,
                "ds-delayed-keepInstances", "ds-delayed-keepInstances-help", this.configuration.keepInstances() ) );

        adList.add( new AttributeDefinitionImpl(
                ScrConfiguration.PROP_LOCK_TIMEOUT,
                "ds-lock-timeout-milliseconds",
                "ds-lock-timeout-milliseconds-help",
                AttributeDefinition.LONG,
                new String[] { String.valueOf(this.configuration.lockTimeout())},
                0, null, null) );

        adList.add( new AttributeDefinitionImpl(
                ScrConfiguration.PROP_STOP_TIMEOUT,
                "ds-stop-timeout-milliseconds",
                "ds-stop-timeout-milliseconds-help",
                AttributeDefinition.LONG,
                new String[] { String.valueOf(this.configuration.stopTimeout())},
                0, null, null) );

        adList.add( new AttributeDefinitionImpl(
                ScrConfiguration.PROP_GLOBAL_EXTENDER,
                "ds-global-extender",
                "ds-global-extender-help",
                false ) );

        return new ObjectClassDefinition()
        {

            private final AttributeDefinition[] attrs = adList
                .toArray(new AttributeDefinition[adList.size()]);

            @Override
            public String getName()
            {
                return "org-apache-felix-scr-ScrService-ocd-name";
            }

            @Override
            public InputStream getIcon(int arg0)
            {
                return null;
            }

            @Override
            public String getID()
            {
                return ScrConfiguration.PID;
            }

            @Override
            public String getDescription()
            {
                return "org-apache-felix-scr-ScrService-ocd-help";
            }

            @Override
            public AttributeDefinition[] getAttributeDefinitions(int filter)
            {
                return (filter == OPTIONAL) ? null : attrs;
            }
        };
    }

    private static class AttributeDefinitionImpl implements AttributeDefinition
    {

        private final String id;
        private final String name;
        private final String description;
        private final int type;
        private final String[] defaultValues;
        private final int cardinality;
        private final String[] optionLabels;
        private final String[] optionValues;


        AttributeDefinitionImpl( final String id, final String name, final String description, final boolean defaultValue )
        {
            this( id, name, description, BOOLEAN, new String[]
                { String.valueOf(defaultValue) }, 0, null, null );
        }

        AttributeDefinitionImpl( final String id, final String name, final String description, final int type,
            final String[] defaultValues, final int cardinality, final String[] optionLabels,
            final String[] optionValues )
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.defaultValues = defaultValues;
            this.cardinality = cardinality;
            this.optionLabels = optionLabels;
            this.optionValues = optionValues;
        }


        @Override
        public int getCardinality()
        {
            return cardinality;
        }


        @Override
        public String[] getDefaultValue()
        {
            return defaultValues;
        }


        @Override
        public String getDescription()
        {
            return description;
        }


        @Override
        public String getID()
        {
            return id;
        }


        @Override
        public String getName()
        {
            return name;
        }


        @Override
        public String[] getOptionLabels()
        {
            return optionLabels;
        }


        @Override
        public String[] getOptionValues()
        {
            return optionValues;
        }


        @Override
        public int getType()
        {
            return type;
        }


        @Override
        public String validate( String arg0 )
        {
            return null;
        }
    }
}
/* @generated */