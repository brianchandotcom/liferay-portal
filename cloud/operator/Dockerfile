FROM golang:1.24 AS builder

WORKDIR /workspace

COPY go.mod go.mod
COPY go.sum go.sum

RUN go mod download && go mod verify

COPY main.go main.go
COPY internal/ internal/

RUN CGO_ENABLED=0 go build -a -ldflags="-s -w" -o manager main.go

FROM gcr.io/distroless/static:nonroot
WORKDIR /
COPY --from=builder /workspace/manager .
USER 65532:65532

ENTRYPOINT ["/manager"]
