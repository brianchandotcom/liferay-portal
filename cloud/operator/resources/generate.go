// Code generation for the operator. Run `go generate ./...` from this directory
// to regenerate the deepcopy functions and CRD manifests. controller-gen is
// version-pinned through the `tool` directive in go.mod — see ../CODEGEN.md
// before changing it.

//go:generate go tool controller-gen object paths=./api/...
//go:generate go tool controller-gen crd paths=./... output:crd:artifacts:config=config/crd/bases

package main
