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
package org.apache.felix.eventadmin.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * The optional meta type provider for the event admin config.
 *
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
public class MetaTypeProviderImpl
    implements MetaTypeProvider, ManagedService
{
    private final int m_threadPoolSize;
    private final int m_timeout;
    private final boolean m_requireTopic;
    private final String[] m_ignoreTimeout;
    private final String[] m_ignoreTopic;
    private final double m_asyncThreadPoolRatio;

    private final ManagedService m_delegatee;

    public MetaTypeProviderImpl(final ManagedService delegatee,
            final int threadPoolSize,
            final int timeout, final boolean requireTopic,
            final String[] ignoreTimeout,
            final String[] ignoreTopic,
            final double asyncThreadPoolRatio)
    {
        m_threadPoolSize = threadPoolSize;
        m_timeout = timeout;
        m_requireTopic = requireTopic;
        m_delegatee = delegatee;
        m_ignoreTimeout = ignoreTimeout;
        m_ignoreTopic = ignoreTopic;
        m_asyncThreadPoolRatio = asyncThreadPoolRatio;
    }

    private ObjectClassDefinition ocd;

    /**
     * @see org.osgi.service.cm.ManagedService#updated(java.util.Dictionary)
     */
    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException
    {
        m_delegatee.updated(properties);
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
        if ( !Configuration.PID.equals( id ) )
        {
            return null;
        }

        if ( ocd == null )
        {
            final ArrayList<AttributeDefinition> adList = new ArrayList<AttributeDefinition>();

            adList.add( new AttributeDefinitionImpl( Configuration.PROP_THREAD_POOL_SIZE, "org-apache-felix-eventadmin-ThreadPoolSize",
                "org-apache-felix-eventadmin-ThreadPoolSize-help",
                m_threadPoolSize ) );
            adList.add( new AttributeDefinitionImpl( Configuration.PROP_ASYNC_TO_SYNC_THREAD_RATIO, "org-apache-felix-eventadmin-AsyncToSyncThreadRatio",
                    "org-apache-felix-eventadmin-AsyncToSyncThreadRatio-help",
                    m_asyncThreadPoolRatio));

            adList.add( new AttributeDefinitionImpl( Configuration.PROP_TIMEOUT, "org-apache-felix-eventadmin-Timeout",
                    "org-apache-felix-eventadmin-Timeout-help",
                    m_timeout ) );

            adList.add( new AttributeDefinitionImpl( Configuration.PROP_REQUIRE_TOPIC, "org-apache-felix-eventadmin-RequireTopic",
                    "org-apache-felix-eventadmin-RequireTopic-help",
                    m_requireTopic ) );
            adList.add( new AttributeDefinitionImpl( Configuration.PROP_IGNORE_TIMEOUT, "org-apache-felix-eventadmin-IgnoreTimeout",
                    "org-apache-felix-eventadmin-IgnoreTimeout-help",
                    AttributeDefinition.STRING, m_ignoreTimeout, Integer.MAX_VALUE, null, null));
            adList.add( new AttributeDefinitionImpl( Configuration.PROP_IGNORE_TOPIC, "org-apache-felix-eventadmin-IgnoreTopic",
                    "org-apache-felix-eventadmin-IgnoreTopic-help",
                    AttributeDefinition.STRING, m_ignoreTopic, Integer.MAX_VALUE, null, null));
            ocd = new ObjectClassDefinition()
            {

                private final AttributeDefinition[] attrs = adList
                    .toArray( new AttributeDefinition[adList.size()] );


                @Override
                public String getName()
                {
                    return "org-apache-felix-eventadmin-ocd-name";
                }


                @Override
                public InputStream getIcon( int arg0 )
                {
                    return null;
                }


                @Override
                public String getID()
                {
                    return Configuration.PID;
                }


                @Override
                public String getDescription()
                {
                    return "org-apache-felix-eventadmin-ocd-help";
                }


                @Override
                public AttributeDefinition[] getAttributeDefinitions( int filter )
                {
                    return ( filter == OPTIONAL ) ? null : attrs;
                }
            };
        }

        return ocd;
    }

    class AttributeDefinitionImpl implements AttributeDefinition
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

        AttributeDefinitionImpl( final String id, final String name, final String description, final int defaultValue )
        {
            this( id, name, description, INTEGER, new String[]
                { String.valueOf(defaultValue) }, 0, null, null );
        }

        AttributeDefinitionImpl( final String id, final String name, final String description, final double defaultValue )
        {
            this( id, name, description, DOUBLE, new String[]
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