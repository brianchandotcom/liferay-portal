@spira_Pagination @Pagination @Segments @List @team_FARO @priority_3
Feature: Paginate Segment list
	As a Business User, I should be able to paginate segment list and view I am on second page

	Background: [Setup] Create 4 segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Ava Wiza"
		* I click the checkbox on the table row containing "Ava Wiza"
		* I click the "Add" button
		* I name the Static segment "CreateStaticIndividualsSegment1 - ${Random.1}"  and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Alena Will"
		* I click the checkbox on the table row containing "Alena Will"
		* I click the "Add" button
		* I name the Static segment "CreateStaticIndividualsSegment2 - ${Random.1}"  and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Marty Grady"
		* I click the checkbox on the table row containing "Marty Grady"
		* I click the "Add" button
		* I name the Static segment "CreateStaticIndividualsSegment3 - ${Random.1}" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Ryan Von"
		* I click the checkbox on the table row containing "Ryan Von"
		* I click the "Add" button
		* I name the Static segment "CreateStaticIndividualsSegment4 - ${Random.1}" and save it

	Scenario: Paginate to second page of segment list
		Given I go to the "Segments" page
		When I set the pagination delta to "5"
		And I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see a segment named "everybody" in the table