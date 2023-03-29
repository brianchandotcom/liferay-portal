@spira_Search @Search @Accounts @List @team_FARO @priority_4
Feature: Search an Account's Associated Segments
	As an Business User, I should be able to search for segments associated with the account

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Adrienne Johnston"
		* I click the checkbox on the table row containing "Adrienne Johnston"
		* I click the "Add" button
		* I name the Static segment "SearchAccountAssociatedSegments Segment - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Austin Ruecker"
		* I click the checkbox on the table row containing "Austin Ruecker"
		* I click the "Add" button
		* I name the Static segment "Unassociated Segment - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Annita Nader"
		* I click the checkbox on the table row containing "Annita Nader"
		* I click the "Add" button
		* I name the Static segment "UPPER SEGMENT - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Elli Kulas"
		* I click the checkbox on the table row containing "Elli Kulas"
		* I click the "Add" button
		* I name the Static segment "lower segment - ${Random.1}" and save it
		* I go to the "Accounts" page
		* I search for "Kertzmann, Kilback and Watsica"
		* I click "Kertzmann, Kilback and Watsica" in the table
		* I click the "Segments" tab

	Scenario: Search an Account's Segments
#	Scenario: Search a Segment that is not associated with an Account
		When I search for "Unassociated Segment - ${Random.1}"
		Then I should see a message that there are no "Associated Segments" found

#	Scenario: Search for a Segment - full name
		When I search for "SearchAccountAssociatedSegments Segment - ${Random.1}"
		Then I should only see a segment named "SearchAccountAssociatedSegments Segment - ${Random.1}" in the table

#	Scenario: Search for a Segment - partial name
		When I search for "SearchAccou"
		Then I should only see a segment named "SearchAccountAssociatedSegments Segment - ${Random.1}" in the table

#	Scenario: Search for a Segment - upper case
		When I search for "UPPER SEGMENT - ${Random.1}"
		Then I should only see a segment named "UPPER SEGMENT - ${Random.1}" in the table
#	Scenario: Search for a Segment - lower case
		When I search for "lower segment - ${Random.1}"
		Then I should only see a segment named "lower segment - ${Random.1}" in the table