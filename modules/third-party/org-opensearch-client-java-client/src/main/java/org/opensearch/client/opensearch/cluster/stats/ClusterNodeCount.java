/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

package org.opensearch.client.opensearch.cluster.stats;

import jakarta.json.stream.JsonGenerator;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.opensearch.client.json.JsonpDeserializable;
import org.opensearch.client.json.JsonpDeserializer;
import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.json.JsonpSerializable;
import org.opensearch.client.json.ObjectBuilderDeserializer;
import org.opensearch.client.json.ObjectDeserializer;
import org.opensearch.client.util.ApiTypeHelper;
import org.opensearch.client.util.ObjectBuilder;
import org.opensearch.client.util.ObjectBuilderBase;

// typedef: cluster.stats.ClusterNodeCount

@JsonpDeserializable
public class ClusterNodeCount implements JsonpSerializable {
    private final int coordinatingOnly;

    private final int data;

    private final int ingest;

    private final int clusterManager;

    private final int total;


    @Nullable
    private Integer votingOnly;

    @Nullable
    private Integer dataCold;

    @Nullable
    private Integer dataFrozen;

    @Nullable
    private Integer dataContent;

    @Nullable
    private Integer dataWarm;

    @Nullable
    private Integer dataHot;

    @Nullable
    private Integer ml;

    private final int remoteClusterClient;

    @Nullable
    private Integer transform;

    // ---------------------------------------------------------------------------------------------

    private ClusterNodeCount(Builder builder) {

        this.coordinatingOnly = ApiTypeHelper.requireNonNull(builder.coordinatingOnly, this, "coordinatingOnly");
        this.data = ApiTypeHelper.requireNonNull(builder.data, this, "data");
        this.ingest = ApiTypeHelper.requireNonNull(builder.ingest, this, "ingest");
        this.clusterManager = ApiTypeHelper.requireNonNull(builder.clusterManager, this, "clusterManager");
        this.total = ApiTypeHelper.requireNonNull(builder.total, this, "total");
        this.votingOnly = builder.votingOnly;
        this.dataCold = builder.dataCold;
        this.dataFrozen = builder.dataFrozen;
        this.dataContent = builder.dataContent;
        this.dataWarm = builder.dataWarm;
        this.dataHot = builder.dataHot;
        this.ml = builder.ml;
        this.remoteClusterClient = ApiTypeHelper.requireNonNull(builder.remoteClusterClient, this, "remoteClusterClient");
        this.transform = builder.transform;

    }

    public static ClusterNodeCount of(Function<Builder, ObjectBuilder<ClusterNodeCount>> fn) {
        return fn.apply(new Builder()).build();
    }

    /**
     * Required - API name: {@code coordinating_only}
     */
    public final int coordinatingOnly() {
        return this.coordinatingOnly;
    }

    /**
     * Required - API name: {@code data}
     */
    public final int data() {
        return this.data;
    }

    /**
     * Required - API name: {@code ingest}
     */
    public final int ingest() {
        return this.ingest;
    }

    /**
     * Required - API name: {@code clusterManager}
     */
    public final int clusterManager() {
        return this.clusterManager;
    }

    /**
     * Required - API name: {@code total}
     */
    public final int total() {
        return this.total;
    }

    /**
     * API name: {@code voting_only}
     */
    @Nullable
    public final Integer votingOnly() {
        return this.votingOnly;
    }

    /**
     * API name: {@code data_cold}
     */
    @Nullable
    public final Integer dataCold() {
        return this.dataCold;
    }

    /**
     * API name: {@code data_frozen}
     */
    @Nullable
    public final Integer dataFrozen() {
        return this.dataFrozen;
    }

    /**
     * API name: {@code data_content}
     */
    @Nullable
    public final Integer dataContent() {
        return this.dataContent;
    }

    /**
     * API name: {@code data_warm}
     */
    @Nullable
    public final Integer dataWarm() {
        return this.dataWarm;
    }

    /**
     * API name: {@code data_hot}
     */
    @Nullable
    public final Integer dataHot() {
        return this.dataHot;
    }

    /**
     * API name: {@code ml}
     */
    @Nullable
    public final Integer ml() {
        return this.ml;
    }

    /**
     * Required - API name: {@code remote_cluster_client}
     */
    public final int remoteClusterClient() {
        return this.remoteClusterClient;
    }

    /**
     * API name: {@code transform}
     */
    @Nullable
    public final Integer transform() {
        return this.transform;
    }

    /**
     * Serialize this object to JSON.
     */
    public void serialize(JsonGenerator generator, JsonpMapper mapper) {
        generator.writeStartObject();
        serializeInternal(generator, mapper);
        generator.writeEnd();
    }

    protected void serializeInternal(JsonGenerator generator, JsonpMapper mapper) {

        generator.writeKey("coordinating_only");
        generator.write(this.coordinatingOnly);

        generator.writeKey("data");
        generator.write(this.data);

        generator.writeKey("ingest");
        generator.write(this.ingest);

        generator.writeKey("cluster_manager");
        generator.write(this.clusterManager);

        generator.writeKey("total");
        generator.write(this.total);

        if (this.votingOnly != null) {
            generator.writeKey("voting_only");
            generator.write(this.votingOnly);
        }

        if (this.dataCold != null) {
            generator.writeKey("data_cold");
            generator.write(this.dataCold);
        }

        if (this.dataFrozen != null) {
            generator.writeKey("data_frozen");
            generator.write(this.dataFrozen);
        }

        if (this.dataFrozen != null) {
            generator.writeKey("data_content");
            generator.write(this.dataContent);
        }

        if (this.dataFrozen != null) {
            generator.writeKey("data_warm");
            generator.write(this.dataWarm);
        }

        if (this.dataFrozen != null) {
            generator.writeKey("data_hot");
            generator.write(this.dataHot);
        }

        if (this.dataFrozen != null) {
            generator.writeKey("ml");
            generator.write(this.ml);
        }

        generator.writeKey("remote_cluster_client");
        generator.write(this.remoteClusterClient);

        if (this.transform != null) {
            generator.writeKey("transform");
            generator.write(this.transform);
        }

    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Builder for {@link ClusterNodeCount}.
     */

    public static class Builder extends ObjectBuilderBase implements ObjectBuilder<ClusterNodeCount> {
        private Integer coordinatingOnly;

        private Integer data;

        private Integer ingest;

        private Integer clusterManager;

        private Integer total;

        @Nullable
        private Integer votingOnly;

        @Nullable
        private Integer dataCold;

        @Nullable
        private Integer dataFrozen;

        @Nullable
        private Integer dataContent;

        @Nullable
        private Integer dataWarm;

        @Nullable
        private Integer dataHot;

        @Nullable
        private Integer ml;

        private Integer remoteClusterClient;

        @Nullable
        private Integer transform;

        /**
         * Required - API name: {@code coordinating_only}
         */
        public final Builder coordinatingOnly(int value) {
            this.coordinatingOnly = value;
            return this;
        }

        /**
         * Required - API name: {@code data}
         */
        public final Builder data(int value) {
            this.data = value;
            return this;
        }

        /**
         * Required - API name: {@code ingest}
         */
        public final Builder ingest(int value) {
            this.ingest = value;
            return this;
        }

        /**
         * Required - API name: {@code clusterManager}
         */
        public final Builder clusterManager(int value) {
            this.clusterManager = value;
            return this;
        }

        /**
         * Required - API name: {@code total}
         */
        public final Builder total(int value) {
            this.total = value;
            return this;
        }

        /**
         * Required - API name: {@code voting_only}
         */
        public final Builder votingOnly(@Nullable Integer value) {
            this.votingOnly = value;
            return this;
        }

        /**
         * Required - API name: {@code data_cold}
         */
        public final Builder dataCold(int value) {
            this.dataCold = value;
            return this;
        }

        /**
         * API name: {@code data_frozen}
         */
        public final Builder dataFrozen(@Nullable Integer value) {
            this.dataFrozen = value;
            return this;
        }

        /**
         * Required - API name: {@code data_content}
         */
        public final Builder dataContent(@Nullable Integer value) {
            this.dataContent = value;
            return this;
        }

        /**
         * Required - API name: {@code data_warm}
         */
        public final Builder dataWarm(@Nullable Integer value) {
            this.dataWarm = value;
            return this;
        }

        /**
         * Required - API name: {@code data_hot}
         */
        public final Builder dataHot(@Nullable Integer value) {
            this.dataHot = value;
            return this;
        }

        /**
         * Required - API name: {@code ml}
         */
        public final Builder ml(@Nullable Integer value) {
            this.ml = value;
            return this;
        }

        /**
         * Required - API name: {@code remote_cluster_client}
         */
        public final Builder remoteClusterClient(int value) {
            this.remoteClusterClient = value;
            return this;
        }

        /**
         * Required - API name: {@code transform}
         */
        public final Builder transform(@Nullable Integer value) {
            this.transform = value;
            return this;
        }

        /**
         * Builds a {@link ClusterNodeCount}.
         *
         * @throws NullPointerException
         *             if some of the required fields are null.
         */
        public ClusterNodeCount build() {
            _checkSingleUse();

            return new ClusterNodeCount(this);
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Json deserializer for {@link ClusterNodeCount}
     */
    public static final JsonpDeserializer<ClusterNodeCount> _DESERIALIZER = ObjectBuilderDeserializer.lazy(
        Builder::new,
        ClusterNodeCount::setupClusterNodeCountDeserializer
    );

    protected static void setupClusterNodeCountDeserializer(ObjectDeserializer<ClusterNodeCount.Builder> op) {

        op.add(Builder::coordinatingOnly, JsonpDeserializer.integerDeserializer(), "coordinating_only");
        op.add(Builder::data, JsonpDeserializer.integerDeserializer(), "data");
        op.add(Builder::ingest, JsonpDeserializer.integerDeserializer(), "ingest");
        op.add(Builder::clusterManager, JsonpDeserializer.integerDeserializer(), "cluster_manager");
        op.add(Builder::total, JsonpDeserializer.integerDeserializer(), "total");
        op.add(Builder::votingOnly, JsonpDeserializer.integerDeserializer(), "voting_only");
        op.add(Builder::dataCold, JsonpDeserializer.integerDeserializer(), "data_cold");
        op.add(Builder::dataFrozen, JsonpDeserializer.integerDeserializer(), "data_frozen");
        op.add(Builder::dataContent, JsonpDeserializer.integerDeserializer(), "data_content");
        op.add(Builder::dataWarm, JsonpDeserializer.integerDeserializer(), "data_warm");
        op.add(Builder::dataHot, JsonpDeserializer.integerDeserializer(), "data_hot");
        op.add(Builder::ml, JsonpDeserializer.integerDeserializer(), "ml");
        op.add(Builder::remoteClusterClient, JsonpDeserializer.integerDeserializer(), "remote_cluster_client");
        op.add(Builder::transform, JsonpDeserializer.integerDeserializer(), "transform");

    }

}
/* @generated */
