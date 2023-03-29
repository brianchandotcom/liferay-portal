@spira_Individuals @Individuals @Dashboard @team_FARO @priority_4 @blocked
Feature: Assert Enriched Profiles change when property is updated
  As a User, I would like to see how many enriched profiles I have on the Individuals' dashboard page

  Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
    * I go to the "Home" page
    * I login as "test@faro.io:test"
    * I should see the "Sites" page
    * I go to the "Data Source" page
    * I click the "Add Data Source" button
    * I create a file named "AssertEnrichedProfileUpdate - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Pilot
			"""
    * I click the "CSV File" button
    * I browse for a file named "AssertEnrichedProfileUpdate - ${Random.1}.csv"
    * I name the Data Source "AssertEnrichedProfileUpdate - ${Random.1}.csv"
    * I click the "Next" button
    * I click the "Done" button
    * I should see that "AssertEnrichedProfileUpdate - ${Random.1}.csv" was successfully uploaded

  Scenario: Enriched Profiles increases when a new property is added to an individual
    Given I go to the "Data Source" page
    And I click the "Add Data Source" button
    And I create a file named "AssertEnrichedProfileUpdate - ${Random.3}.csv" with the following content:
			"""
			email,givenName,familyName,industry
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Aerospace
			"""
    And I click the "CSV File" button
    And I browse for a file named "AssertEnrichedProfileUpdate - ${Random.3}.csv"
    And I name the Data Source "AssertEnrichedProfileUpdate - ${Random.3}.csv"
    And I click the "Next" button
    And I click the "Done" button
    And I should see that "AssertEnrichedProfileUpdate - ${Random.3}.csv" was successfully uploaded
    When I go to the "Individuals" page
    Then I should see the "Enriched Profiles" count is 101
    And I should see the "Total" count is 201
    And I should see the "Known" count is 201
    And I should see the "Anonymous" count is 0