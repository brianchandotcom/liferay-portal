.PHONY: all build generate verify clean

all: build

clean:
	rm -f manager

build:
	go build -o manager .

generate:
	go generate ./...

verify:
	go build ./...
	go vet ./...