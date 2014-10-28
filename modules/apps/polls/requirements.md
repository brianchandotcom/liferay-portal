# Polls

[[@]] Why this document? Simply because we need to know what business behaviors
we want to be implement. This is VERY important! For example, existence of `Votes`
depends on the requirements. If all we need to track is _votes count_ then
`Votes` entity is **NOT** needed! However, if we need to track votes e.g. in time
and to give some reports later, then we do need to keep track of individual votes.


## Story

As a user
I want to create a poll question with several choices
So that other users can vote on the choices.

### Scenario: create poll

Given user is on polls creation page
And user is authenticated
When user enters question and choices 
Then poll is created

## Story

As a any user
I want to vote for a choice
So to have my vote counts.

### Scenario: vote for a choice

Given user is on poll page
When he chooses a choice
Then choice count is incremented

[[@]] This last line is actually telling us that we do NOT need a vote table.