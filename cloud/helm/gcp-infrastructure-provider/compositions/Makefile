MOCK ?= mock.json

LAYERS = backup elasticsearch init k8s_resources managed_service_details overlay security sql storage

.PHONY: all clean fmt generate generate-models lint lint-layers pkg run run-all test $(LAYERS)

all: fmt lint test

clean:
	rm -rf vendor/ kcl.mod.lock dist/

fmt:
	kcl fmt *.k
	kcl fmt tests/*.k

generate:
	./scripts/generate_kcl_bindings.sh

generate-models:
	./scripts/generate_models.sh

# Type-check the bundled pipeline against an empty input.
lint: generate
	@kcl run dist/pipeline.k -D params='{"oxr": {"spec": {"database": {}}}, "ctx": {"apiextensions.crossplane.io/environment": {}}}' > /dev/null

# Type-check each source layer standalone. Faster feedback loop when
# iterating on a single layer; does not regenerate the bundle.
lint-layers:
	@for layer in $(LAYERS); \
	do \
		kcl run $${layer}.k -D params='{"oxr": {"spec": {"database": {}}}, "ctx": {"apiextensions.crossplane.io/environment": {}}}' > /dev/null || exit 1; \
	done

pkg: generate
	cd dist && kpm pkg

# Render the full composite (all layers aggregated via main.k) using MOCK.
run:
	@if [ ! -f $(MOCK) ]; then echo "Please provide a mock.json file."; exit 1; fi
	@kcl run main.k -D params=$$(cat $(MOCK) | jq -r '.params | tojson')

# Render the bundled dist/pipeline.k using MOCK (what crossplane runs).
run-all: generate
	@if [ ! -f $(MOCK) ]; then echo "Please provide a mock.json file."; exit 1; fi
	@kcl run dist/pipeline.k -D params=$$(cat $(MOCK) | jq -r '.params | tojson')

test:
	@PARAMS=$$(cat tests/arz_dev2025_params.json | jq -c) && \
	CLUSTER_REFS=$$(cat tests/cluster_extracted_resources.json | jq -c) && \
	cd tests && kcl test . -D params="$$PARAMS" -D cluster_refs="$$CLUSTER_REFS"

# Per-layer render: `make backup` prints only that layer's YAML for MOCK.
$(LAYERS):
	@if [ ! -f $(MOCK) ]; then echo "Please provide a mock.json file."; exit 1; fi
	@kcl run $@.k -D params=$$(cat $(MOCK) | jq -r '.params | tojson')
