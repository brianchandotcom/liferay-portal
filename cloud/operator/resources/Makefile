.PHONY: all build clean generate verify

all: build

build:
	go build -o manager .

clean:
	rm -f manager

generate:
	go generate ./...

verify:
	go build ./...
	go vet ./...