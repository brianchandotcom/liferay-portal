@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4 @blocked
Feature: Create a Dynamic Individuals Segment with a criteria that uses a boolean and a does not contain
	As a Business User, I should be able to create a Dynamic Individuals Segment

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,boolean
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist,true
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Project Manager,true
			${Random.4}@gmail.com,first${Random.4},last${Random.4},Civil Engineer,false
			"""
		* I click the "CSV File" button
		* I browse for a file named "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}.csv"
		* I name the Data Source "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped
		* I click the "Select Analytics Cloud Field" dropdown
		* I click the "New Field" button
		* I type "boolean" into the "New Field Name" input field
		* I click the "Select" dropdown
		* I click the "Boolean" dropdown option from the dropdown overlay
		* I click the "Create" button
		* I click the "Done" button
		* I should see that "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Create the Dynamic Individuals Segment Using does not contain
		Given I click the "Dynamic Segment" dropdown option
		And I select "Individual Attributes" from the criterion type dropdown
		When I create a criteria with the following conditions:
			| jobTitle | does not contain | economist |
		And I create a criteria with the following conditions:
			| boolean | TRUE |
		And I name the Dynamic segment "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}"
		Then I should see a "Segment" named "CreateDynamicIndividualsSegmentBooleanAndDoesNotContain - ${Random.1}" with "1" items