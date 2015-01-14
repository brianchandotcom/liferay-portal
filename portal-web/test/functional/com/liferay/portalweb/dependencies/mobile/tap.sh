# !/bin/sh

sendevent /dev/input/event0 3 0 $1

sendevent /dev/input/event0 3 1 $2

sendevent /dev/input/event0 1 330 1

sendevent /dev/input/event0 0 0 0

sleep 0.2

sendevent /dev/input/event0 1 330 0

sendevent /dev/input/event0 0 0 0