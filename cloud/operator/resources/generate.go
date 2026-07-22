//go:generate go tool controller-gen object paths=./api/...
//go:generate go tool controller-gen crd paths=./... output:crd:artifacts:config=config/crd/bases

package main